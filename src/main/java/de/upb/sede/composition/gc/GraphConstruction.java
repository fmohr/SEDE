package de.upb.sede.composition.gc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.Graph;
import de.upb.sede.composition.graphs.GraphTraversal;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.ParseConstantNode;
import de.upb.sede.composition.graphs.nodes.ReceiveDataNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.exceptions.CompositionSemanticException;
import de.upb.sede.exceptions.GraphFormException;

/**
 * This class contains all the logic to construct a graph.
 * 
 * @author aminfaez
 *
 */
public class GraphConstruction {

	private final List<InstructionNode> unresolvedInstructionNodes;

	private final Map<ExecutorHandle, Graph> graphs;

	private final Graph orderOfExecutionGraph;

	private final ResolveInfo resolveInfo;

	/**
	 */
	private GraphConstruction(ResolveInfo resolveInfo) {
		this.resolveInfo = resolveInfo;

		this.graphs = new HashMap<>();
		this.orderOfExecutionGraph = new Graph();
		this.unresolvedInstructionNodes = new ArrayList<>();

		/*
		 * Create empty graph for client.
		 */
		this.graphs.put(resolveInfo.getClientInfo().getClientExecutor(), new Graph());
	}

	/**
	 */
	public GraphConstruction(ResolveInfo resolveInfo, List<InstructionNode> instNodes) {
		this(resolveInfo);
		createOrderedInstructionGraph(instNodes);
	}

	public void createOrderedInstructionGraph(List<InstructionNode> instNodes) {
		BaseNode lastNode = null;
		for (Iterator<InstructionNode> it = instNodes.iterator(); it.hasNext();) {
			BaseNode currentNode = it.next();
			orderOfExecutionGraph.addNode(currentNode);
			if (lastNode != null) {
				/*
				 * this isn't the first instruction node added.
				 */
				orderOfExecutionGraph.connectNodes(lastNode, currentNode);
			}
			lastNode = currentNode;
		}
	}

	/**
	 * Resolving context of an instruction means the assignment of a specific
	 * executor to carry out the instruction. This method plans the execution of the
	 * instruction by putting the given instruction node into the graphs map. If it
	 * doesn't support the service that instruction asks for
	 */
	private void resolveContext(InstructionNode instNode) {
		String serviceClass = instNode.getServiceClass(resolveInfo);
		boolean resolvedContext = false; /*
											 * flag that indicates that the node has been added to the graphs map and
											 * therefore resolved
											 */
		if (resolveInfo.getClassesConfig().classknown(serviceClass)) {
			/*
			 * class is known.
			 */
			if (instNode.isAssignedHost()) {
				/*
				 * Explicit host is defined.
				 */
				if (resolveInfo.getExecutorCoordinator().hasExecutor(instNode.getHost())) {
					/*
					 * The addressed executor is known to this gateway:
					 */
					ExecutorHandle execHandle = resolveInfo.getExecutorCoordinator().getExecutorFor(instNode.getHost());
					if (!execHandle.getExecutionerCapabilities()
							.supportsServiceClass(instNode.getServiceClass(resolveInfo))) {
						throw new CompositionSemanticException("Instruction, " + instNode.toString()
								+ ", explicitly addresses an executor which doesn't support the given service.");
					}
					if (!graphs.containsKey(execHandle)) {
						graphs.put(execHandle, new Graph());
					}
					graphs.get(execHandle).addNode(instNode);
					resolvedContext = true;
				}
				/*
				 * if the host of the explicit stated executor is unknown, resolvedContext
				 * remains false and the instruction will be added to the
				 * unresolvedInstructionList.
				 */
			} else {
				/*
				 * Try to find an executor that supports the service class:
				 */
				List<ExecutorHandle> supportingExecutor = resolveInfo.getExecutorCoordinator()
						.executorsSupportingServiceClass(serviceClass);
				if (!supportingExecutor.isEmpty()) {
					/*
					 * There is at least one executor that can carry out the instruction.
					 * Try to pick an executor that was already selected for another instruction:
					 */
					for (ExecutorHandle eh : supportingExecutor) {
						if (graphs.containsKey(eh)) {
							graphs.get(eh).addNode(instNode);
							resolvedContext = true;
							break;
						}
					}
					if (!resolvedContext) {
						/*
						 * No executor in the supportingExecutor list was found that is already assigned
						 * to do another instruction. Pick a new random executor and create a new graph
						 * for it. TODO do scheduling based on load between executors.
						 */
						int randomIndex = (int) (supportingExecutor.size() * Math.random());
						ExecutorHandle randomExecutor = supportingExecutor.get(randomIndex);
						Graph newExecutorGraph = new Graph();
						newExecutorGraph.addNode(instNode);
						graphs.put(randomExecutor, newExecutorGraph);
					}
				}
			}
		}
		if (!resolvedContext) {
			/*
			 * Context of the given node cannot be resolved. Put it into the unresolved
			 * list:
			 */
			unresolvedInstructionNodes.add(instNode);
		}
	}

	/**
	 * For every graph invoke resolveDataFlowDependency only once as it creates node in the client graph.
	 * 
	 */
	public void resolveDataFlowConsumerDependency(final Graph graph) {
		if(graph.containsEdges()) {
			throw new GraphFormException("data flow resolve method was called on a graph with edges.");
		}
		/*
		 * Create list of the nodes  in the given graph based on the order of instruction.
		 */
		List<BaseNode> nodes = GraphTraversal.topologicalSort(orderOfExecutionGraph);
		nodes.removeIf(n -> !graph.contains(n));
		for(BaseNode currentNode : nodes) {
			if(!(currentNode instanceof InstructionNode)) {
				throw new GraphFormException("The graph already contains nodes other that instruction node: " + currentNode.toString());
			}
			InstructionNode iN = (InstructionNode) currentNode;
			for(String fieldname : iN.consumingFields(resolveInfo)) {
				boolean foundProducer = false;
				for(BaseNode producer : GraphTraversal.fieldnameProducingNodes(graph, fieldname, resolveInfo)) {
					foundProducer = true;
					if(!GraphTraversal.isTherePathFromTo(orderOfExecutionGraph, iN, producer)) {
						/*
						 * The producer is not an instruction below the current instruction node. 
						 * So it is safe to add an edge without creating a cycle.
						 */
						if(iN.getContext().equals(fieldname)) {
							/*
							 * Connect service dependency
							 */
							graph.connectNodes(producer, iN);
						}else {
							/*
							 * Connect data flow dependency:
							 */
							connectDataReceiver(graph, producer, iN);
						}
					}
				}
				if(!foundProducer) {
					/*
					 * No producer found for the given field. create a node that produces it:
					 */
					if(FMCompositionParser.isConstant(fieldname)) {
						/*
						 * A constant needs a parser node:
						 */
						ParseConstantNode parser = new ParseConstantNode(fieldname);
						graph.addNode(parser);
						graph.connectNodes(parser, iN);
					}
					else {
						if(iN.getContext().equals(fieldname)) {
							/*
							 * fieldname is a service here. 
							 * Load service before its execution:
							 */
							ServiceInstanceHandle sh = resolveInfo.getInputInformation().getServiceInstanceHandle(fieldname);
							ServiceInstanceStorageNode storageNode = new ServiceInstanceStorageNode(true, fieldname, sh.getClasspath(), sh.getId());
							graph.addNode(storageNode);
							graph.connectNodes(storageNode, iN);
						} else {
							/*
							 * fieldname is some sort of data dependency.
							 * Create a receiver node and connect it to instructionNode
							 */
							ReceiveDataNode receiver = new ReceiveDataNode(fieldname);
							graph.addNode(receiver);
							connectDataReceiver(graph, receiver, iN);
						}
					}
				}
			}
			
		}
		
	}
	/*
	 * constructs a path from producer to consumer in the given graph to indicate the dependency.
	 * For now it simply adds an edge to the graph.
	 * TODO check if the method signature and provided data type match. Add marshal nodes.
	 */
	private void connectDataReceiver(Graph graph, BaseNode producer, BaseNode consumer) {
		graph.connectNodes(producer, consumer);
	}
	
	public void forwardUnresolvedGraph() {
		if(!unresolvedInstructionNodes.isEmpty()) {
			throw new GraphFormException("Unsupported composition. Currenctly the forwarding of the graph is not ");
		}
	}
	
}
