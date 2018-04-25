package de.upb.sede.composition.gc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.GraphTraversal;
import de.upb.sede.composition.graphs.nodes.AcceptDataNode;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.CastTypeNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.ParseConstantNode;
import de.upb.sede.composition.graphs.nodes.SendGraphNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;
import de.upb.sede.composition.graphs.nodes.TransmitDataNode;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;

public class GraphConstruction {

	private final List<Execution> executions;

	private final Execution clientExecution;

	private final DataFlowAnalysis dataFlow;

	private final ResolveInfo resolveInfo;

	public final static GraphConstruction resolveClientGraph(String fmComposition, ResolveInfo resolveInformation) {

		/*
		 * Parse the fm composition
		 */
		List<String> fmInstructionList = FMCompositionParser.separateInstructions(fmComposition);
		List<InstructionNode> instructionList = new ArrayList<>();
		for (String fmInstruction : fmInstructionList) {
			InstructionNode instruction = FMCompositionParser.parseInstruction(fmInstruction);
			instructionList.add(instruction);
		}
		GraphConstruction gc = new GraphConstruction(resolveInformation, Collections.unmodifiableList(instructionList));

		for (InstructionNode instNode : instructionList) {
			Execution exec = gc.resolveExecution(instNode);
			exec.graph.addNode(instNode);
			gc.resolveDependecies(exec, instNode);
		}
		for (String resultFieldname : gc.dataFlow.resultFieldnames()) {
			gc.resolveReturnFields(resultFieldname);
		}
		return gc;

	}

	public CompositionGraph getResolvedClientGraph() {
		/*
		 * Add send graph nodes to the client graph and returns it:
		 */
		CompositionGraph resolvedClientGraph = clientExecution.graph.clone();
		GraphJsonSerializer gjs = new GraphJsonSerializer();
		for (Execution exec : executions) {
			String jsonGraph = gjs.toJson(exec.graph).toJSONString();
			SendGraphNode sendGraph = new SendGraphNode(jsonGraph, exec.executor.getHostAddress());
			resolvedClientGraph.addNode(sendGraph);
		}
		return resolvedClientGraph;
	}

	
	private void resolveReturnFields(String resultFieldname) {
		FieldType resultFieldType = dataFlow.resultFieldtype(resultFieldname);

		if (resolveInfo.getResolvePolicy().isToReturn(resultFieldname)) {

			BaseNode resultProducer = resultFieldType.getProducer();
			Execution resultExecution = execWithNode(resultProducer);
			if (clientExecution != resultExecution) {
				/*
				 * return result to client
				 */
				if (resolveInfo.getResolvePolicy().isToReturn(resultFieldname)) {
					TransmitDataNode transmitResult = new TransmitDataNode(resultFieldname,
							clientExecution.executor.getHostAddress());
					dataFlow.nodeConsumesField(transmitResult, resultFieldType);
					resultExecution.graph.addNode(transmitResult);
					resultExecution.graph.connectNodes(resultProducer, transmitResult);
					AcceptDataNode clientAcceptResult = new AcceptDataNode(resultFieldname);
					dataFlow.nodeConsumesField(clientAcceptResult, resultFieldType);
					clientExecution.graph.addNode(clientAcceptResult);
				}

				if (resultFieldType.isServiceInstance()
						&& resolveInfo.getResolvePolicy().isPersistentService(resultFieldname)) {
					/*
					 * store service instance:
					 */
					ServiceInstanceStorageNode store = new ServiceInstanceStorageNode(false, resultFieldname,
							resultFieldType.getTypeName());
					dataFlow.nodeConsumesField(store, resultFieldType);
					resultExecution.graph.addNode(store);
					resultExecution.graph.connectNodes(resultProducer, store);
				}

			}
		}
	}

	private void resolveDependecies(Execution exec, BaseNode unresolvedNode) {
		CompositionGraph graph = exec.graph;
		for (FieldType dependency : dataFlow.getConsumingFields(unresolvedNode)) {
			List<BaseNode> producers = dataFlow.getProducers(dependency);
			if (producers.size() == 0) {
				throw new RuntimeException("No producers for " + dependency);
			}
			boolean found = false;
			BaseNode localProducer = null;
			for (BaseNode producer : producers) {
				if (graph.contains(producer)) {
					localProducer = producer;
					found = true;
					break;
				}
			}
			if (!found) {
				BaseNode remoteProducer = producers.get(producers.size() - 1);
				if (remoteProducer instanceof ParseConstantNode || remoteProducer instanceof CastTypeNode
						|| remoteProducer instanceof ServiceInstanceStorageNode) {
					/*
					 * the remote producer can be added to this execution:
					 */
					graph.addNode(remoteProducer);
					resolveDependecies(exec, remoteProducer);
					localProducer = remoteProducer;
				} else {
					Execution remoteExec = execWithNode(remoteProducer);
					if (remoteExec == null || remoteExec == exec) {
						throw new RuntimeException("The dependency " + dependency.getFieldname() + " of "
								+ unresolvedNode.toString() + " cannot be resolved.");
					}
					TransmitDataNode transmitDataNode = new TransmitDataNode(dependency.getFieldname(),
							exec.executor.getHostAddress());
					dataFlow.nodeConsumesField(transmitDataNode, dependency);
					remoteExec.graph.addNode(transmitDataNode);
					remoteExec.graph.connectNodes(remoteProducer, transmitDataNode);
					AcceptDataNode acceptDataNode = new AcceptDataNode(dependency.getFieldname());
					graph.addNode(acceptDataNode);
					if (dependency.isServiceInstance() || dependency.isSemanticData()) {
						localProducer = acceptDataNode;
					} else if (dependency.isRealData()) {
						/*
						 * add caster:
						 */
						String semanticType = resolveInfo.getTypeConfig().getOnthologicalType(dependency.getTypeName());
						FieldType receivedSemanticField = new FieldType(acceptDataNode, dependency.getFieldname(),
								TypeClass.SemanticDataType, semanticType, false);
						dataFlow.nodeProducesField(acceptDataNode, receivedSemanticField);
						String casterClasspath = resolveInfo.getTypeConfig()
								.getOnthologicalCaster(dependency.getTypeName());
						CastTypeNode caster = new CastTypeNode(dependency.getFieldname(), semanticType,
								dependency.getTypeName(), false, casterClasspath);
						graph.addNode(caster);
						graph.connectNodes(acceptDataNode, caster);
						dataFlow.nodeConsumesField(caster, receivedSemanticField);
						localProducer = caster;

					} else {
						throw new RuntimeException("Unkown type is being transmitted: " + dependency.getTypeClass());
					}
				}
			}
			if (localProducer == null) {
				throw new RuntimeException("Something went wrong. unresolvedNode: " + unresolvedNode.toString()
						+ " field: " + dependency.getFieldname());
			}
			dataFlow.nodeProducesField(localProducer, dependency);
			graph.connectNodes(localProducer, unresolvedNode);
		}
	}

	private Execution execWithNode(BaseNode containingNode) {
		for (int i = executions.size() - 1; i >= 0; i--) {
			Execution exec = executions.get(i);
			if (exec.graph.contains(containingNode)) {
				return exec;
			}
		}
		throw new RuntimeException("No execution found for " + containingNode.toString());
	}

	private Execution resolveExecution(InstructionNode instNode) {
		Execution resolvedExecution = null;
		if (instNode.isContextAFieldname()) {
			/*
			 * instructions context is field. so it needs to be assigned to the correct
			 * execution.
			 */
			String serviceInstanceFieldname = instNode.getContext();

			FieldType serviceInstanceField = dataFlow.getFieldType(instNode, serviceInstanceFieldname);
			if (serviceInstanceField.getProducer() instanceof ServiceInstanceStorageNode) {
				/*
				 * client sends this input node.
				 */
				ServiceInstanceHandle serviceInstanceHandle = resolveInfo.getInputFields()
						.getServiceInstanceHandle(serviceInstanceFieldname);
				resolvedExecution = getOrCreateExecutionForHost(serviceInstanceHandle.getHost());
			} else {
				/*
				 * The field is bound to a new service instance. producer is another instruction
				 * node.
				 */
				BaseNode producer = serviceInstanceField.getProducer();
				resolvedExecution = getOrFailExecutionForNode(producer);
			}
		} else {
			/*
			 * instructions context is a service classpath.
			 */
			String serviceClasspath = instNode.getContext();
			boolean found = false;
			for (Execution exec : executions) {
				ExecutorHandle executor = exec.executor;
				if (executor.getExecutionerCapabilities().supportsServiceClass(serviceClasspath)) {
					found = true;
					resolvedExecution = exec;
					break;
				}
			}
			if (!found) {
				/*
				 * no execution in the list supports the required service. find a new executor
				 * that supports the service and add it to the exections.
				 */
				ExecutorHandle newExecutor = resolveInfo.getExecutorCoordinator()
						.randomExecutorWithServiceClass(serviceClasspath);
				resolvedExecution = new Execution(newExecutor);
				executions.add(resolvedExecution);
			}
		}
		return Objects.requireNonNull(resolvedExecution);
	}

	private Execution getOrCreateExecutionForHost(String executorHost) {
		if (resolveInfo.getExecutorCoordinator().hasExecutor(executorHost)) {
			ExecutorHandle executor = resolveInfo.getExecutorCoordinator().getExecutorFor(executorHost);
			for (Execution exec : executions) {
				if (exec.executor.equals(executor)) {
					return exec;
				}
			}
			/*
			 * None found
			 */
			Execution exec = new Execution(executor);
			executions.add(exec);
			return exec;

		} else {
			throw new RuntimeException("The specified executor host " + executorHost + " is not specified ");
		}
	}

	private Execution getOrFailExecutionForNode(BaseNode node) {
		for (Execution exec : executions) {
			if (exec.graph.contains(node)) {
				return exec;
			}
		}
		throw new RuntimeException("No execution found for " + node.toString());
	}

	private GraphConstruction(ResolveInfo resolveInfo, List<InstructionNode> instructions) {
		this.resolveInfo = resolveInfo;

		this.dataFlow = new DataFlowAnalysis(resolveInfo, instructions);

		this.clientExecution = new Execution(resolveInfo.getClientInfo().getClientExecutor());

		for (AcceptDataNode bn : dataFlow.getClientInputNodes()) {
			clientExecution.graph.addNode(bn);
		}

		this.executions = new ArrayList<>();

		executions.add(clientExecution);
	}

	static class Execution {
		private final CompositionGraph graph;
		private final ExecutorHandle executor;

		Execution(ExecutorHandle executor) {
			this.graph = new CompositionGraph();
			this.executor = executor;
		}

		public CompositionGraph getGraph() {
			return graph;
		}

		public ExecutorHandle getExecutor() {
			return executor;
		}

	}

	public List<Execution> getExecutions() {
		return executions;
	}
}
