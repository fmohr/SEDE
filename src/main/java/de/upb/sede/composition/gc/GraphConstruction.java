package de.upb.sede.composition.gc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.Graph;
import de.upb.sede.composition.graphs.GraphTraversal;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.ParseConstantNode;
import de.upb.sede.composition.graphs.nodes.ReceiveDataNode;
import de.upb.sede.composition.graphs.nodes.SendDataNode;
import de.upb.sede.composition.graphs.nodes.ServiceInstanceStorageNode;
import de.upb.sede.exceptions.CompositionSemanticException;
import de.upb.sede.exceptions.GraphFormException;
import de.upb.sede.util.DefaultMap;

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
	 * 
	 */
	public GraphConstruction(ResolveInfo resolveInfo, List<InstructionNode> instNodes) {
		this(resolveInfo);
		createOrderedInstructionGraph(instNodes);
	}

	/**
	 * 
	 */
	public void createOrderedInstructionGraph(List<InstructionNode> instNodes) {
		for (Iterator<InstructionNode> it = instNodes.iterator(); it.hasNext();) {
			BaseNode currentNode = it.next();
			orderOfExecutionGraph.addNode(currentNode);
			/*
			 * Connect each preceding node to the current node:
			 */
			for (BaseNode bn : GraphTraversal.iterateNodes(orderOfExecutionGraph)) {

				orderOfExecutionGraph.connectNodes(bn, currentNode);
			}
		}
	}

	/**
	 * 
	 */
	public void createInputNodesOnClientGraph() {
		Graph clientGraph = getClientGraph();
		for (String inputFieldname : resolveInfo.getInputInformation().getInputFields()) {
			ReceiveDataNode receiveDataNode = new ReceiveDataNode(inputFieldname);
			clientGraph.addNode(receiveDataNode);
		}
	}

	/**
	 * Resolving context of an instruction means the assignment of a specific
	 * executor to carry out the instruction. This method plans the execution of the
	 * instruction by putting the given instruction node into the graphs map. If it
	 * doesn't support the service that instruction asks for
	 */
	public void resolveContext(InstructionNode instNode) {
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
				 * Explicit host is defined by the instruction.
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
					 * There is at least one executor that can carry out the instruction. Try to
					 * pick an executor that was already selected for another instruction:
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
	 * For every graph invoke resolveDataFlowDependency only once as it creates node
	 * in the client graph.
	 * 
	 */
	public void resolveDataFlowConsumerDependency(ExecutorHandle executor) {
		Graph graph = getGraphFor(executor);
		if (graph.containsEdges()) {
			throw new GraphFormException("data flow resolve method was called on a graph with edges.");
		}
		/*
		 * Create list of the nodes in the given graph based on the order of
		 * instruction.
		 */
		List<BaseNode> instructionNodes = GraphTraversal.topologicalSort(orderOfExecutionGraph);
		instructionNodes.removeIf(n -> !graph.contains(n));
		/*
		 * Resolve data/service dependency for each node one by one:
		 */
		for (BaseNode currentNode : instructionNodes) {
			if (!(currentNode instanceof InstructionNode)) {
				throw new GraphFormException(
						"The graph already contains nodes other that instruction node: " + currentNode.toString());
			}
			InstructionNode instructionNode = (InstructionNode) currentNode;
			for (String fieldname : instructionNode.consumingFields(resolveInfo)) {
				boolean foundProducer = false; // flag that indicates that a producer was already found.
				/*
				 * Look for a node in graph that already produces the node.
				 */
				for (BaseNode producer : GraphTraversal.iterateNodesProducingFieldname(graph, fieldname, resolveInfo)) {
					if (!GraphTraversal.isTherePathFromTo(orderOfExecutionGraph, instructionNode, producer)) {
						foundProducer = true;
						/*
						 * The producer is not an instruction below the current instruction node. So it
						 * is safe to add an edge without creating a cycle.
						 */
						if (instructionNode.getContext().equals(fieldname)) {
							/*
							 * Connect service dependency
							 */
							graph.connectNodes(producer, instructionNode);
						} else {
							/*
							 * Connect data flow dependency:
							 */
							connectDataReceiver(graph, producer, instructionNode);
						}
					}
				}
				if (!foundProducer) {
					/*
					 * No producer found for the given field. create a node that produces it:
					 */
					if (FMCompositionParser.isConstant(fieldname)) {
						/*
						 * A constant needs a parser node:
						 */
						ParseConstantNode parser = new ParseConstantNode(fieldname);
						graph.addNode(parser);
						graph.connectNodes(parser, instructionNode);
					} else {
						if (instructionNode.getContext().equals(fieldname)) {
							/*
							 * fieldname is a service here. Load service before its execution:
							 */
							ServiceInstanceHandle sh = resolveInfo.getInputInformation()
									.getServiceInstanceHandle(fieldname);
							ServiceInstanceStorageNode storageNode = new ServiceInstanceStorageNode(true, fieldname,
									sh.getClasspath(), sh.getId());
							graph.addNode(storageNode);
							graph.connectNodes(storageNode, instructionNode);
						} else {
							/*
							 * fieldname is some sort of data dependency. Create a receiver node and connect
							 * it to instructionNode
							 */
							ReceiveDataNode receiver = new ReceiveDataNode(fieldname);
							graph.addNode(receiver);
							connectDataReceiver(graph, receiver, instructionNode);
						}
					}
				}
			}

		}
	}

	/**
	 * Only call this function once per graph
	 */
	public void resolveReceivingNodes(ExecutorHandle receivingExecutor) {
		if (!graphs.containsKey(receivingExecutor)) {
			/*
			 * this executor doesn't have a graph.
			 */
			return;
		}

		DefaultMap<BaseNode, Integer> priorityMap = createNodePriorityMap();

		Graph receivingGraph = getGraphFor(receivingExecutor);

		for (BaseNode baseNode : GraphTraversal.iterateNodesWithClassname(receivingGraph,
				ReceiveDataNode.class.getSimpleName())) {
			ReceiveDataNode receiver = (ReceiveDataNode) baseNode;
			int maximumPriority = priorityMap.get(receiver);
			String receivingField = receiver.getReceivingFieldname();
			/*
			 * Find the graph to put this send data note into.
			 */

			SendDataNode sendData = new SendDataNode(receivingField, receivingExecutor.getHostAddress());
			/*
			 * producing graph is the graph that contains nodes that produce the fieldname.
			 * currentPriority is the maximum priority of all node that produces the
			 * fieldname in iterated graphs.
			 */
			Graph producingGraph = null;
			int currentPriority = -2;
			for (ExecutorHandle otherExecutors : graphs.keySet()) {
				if (otherExecutors == receivingExecutor) {
					continue; // only consider other graphs
				}
				Graph otherGraph = getGraphFor(otherExecutors);
				/*
				 * iterate over all nodes that create or change the state of the receivingField
				 */
				for (BaseNode producingNode : GraphTraversal.iterateNodesProducingFieldname(otherGraph, receivingField,
						resolveInfo)) {
					/*
					 * if the field changes producingNodes and its priority is above last
					 * currentPrioty but bellow maximum priority, we found a new graph which
					 * produces the field which needs to be sent to the receiver we are looking at.
					 */
					int producerPriority = priorityMap.get(producingNode);
					if (producerPriority > currentPriority && producerPriority <= maximumPriority) {
						producingGraph = otherGraph;
						break;
					}
				}
			}
			if (producingGraph == null) {
				throw new CompositionSemanticException("The fieldname " + receivingField + " cannot be resolved. "
						+ "That means there is no input bounded to the fieldname "
						+ "nor is there any instruction that creates the fieldname.");
			}
			producingGraph.addNode(sendData);
			for (BaseNode producingNode : GraphTraversal.iterateNodesProducingFieldname(producingGraph, receivingField,
					resolveInfo)) {
				producingGraph.connectNodes(producingNode, sendData);
			}
		}
	}

	public void storeServiceInstances(ExecutorHandle executor) {
		Graph graph = getGraphFor(executor);
		Map<String, ServiceInstanceStorageNode> serviceInstanceFieldnameMap = new HashMap<>();
		/*
		 * calculate the set of all service instance fieldnames which are persistent.
		 */
		for (BaseNode bn : GraphTraversal.iterateNodesWithClassname(graph, InstructionNode.class.getSimpleName())) {
			InstructionNode instructionNode = (InstructionNode) bn;
			/* service instance construction */
			if (instructionNode.isServiceConstruct() && instructionNode.isAssignedLeftSideFieldname()) {
				String serviceInstanceFieldname = instructionNode.getLeftSideFieldname();
				if (resolveInfo.getInputInformation().isServiceInstanceHandle(serviceInstanceFieldname)) {
					throw new CompositionSemanticException(
							"An instruction creates a service instance and tries to rebind a fieldname of a service-instance-handle which is already defined in the input list. Fieldname: "
									+ serviceInstanceFieldname);
				}

				if ((!serviceInstanceFieldnameMap.containsKey(serviceInstanceFieldname))
						&& resolveInfo.getResolvePolicy().isPersistentService(serviceInstanceFieldname)) {
					ServiceInstanceStorageNode saveServiceInstaceNode = new ServiceInstanceStorageNode(
							serviceInstanceFieldname, instructionNode.getContext());
					serviceInstanceFieldnameMap.put(serviceInstanceFieldname, saveServiceInstaceNode);
				}
			}
			/*
			 * service instance method invocation and changing state of the service instance
			 */
			if (instructionNode.isContextAFieldname()
					&& instructionNode.producesField(instructionNode.getContext(), resolveInfo)) {
				String serviceInstanceFieldname = instructionNode.getContext();
				if ((!serviceInstanceFieldnameMap.containsKey(serviceInstanceFieldname))
						&& resolveInfo.getResolvePolicy().isPersistentService(serviceInstanceFieldname)) {
					if (resolveInfo.getInputInformation().isServiceInstanceHandle(serviceInstanceFieldname)) {
						ServiceInstanceHandle sih = resolveInfo.getInputInformation()
								.getServiceInstanceHandle(serviceInstanceFieldname);
						ServiceInstanceStorageNode saveServiceInstaceNode = new ServiceInstanceStorageNode(false,
								serviceInstanceFieldname, sih.getClasspath(), sih.getId());
						serviceInstanceFieldnameMap.put(serviceInstanceFieldname, saveServiceInstaceNode);
					}
				}
			}
		}
		/*
		 * Add the node and all its edges to the graph.
		 */
		for (String serviceInstanceFieldname : serviceInstanceFieldnameMap.keySet()) {
			ServiceInstanceStorageNode saveServiceInstaceNode = serviceInstanceFieldnameMap
					.get(serviceInstanceFieldname);
			graph.addNode(saveServiceInstaceNode);
			for (BaseNode producers : GraphTraversal.iterateNodesProducingFieldname(graph, serviceInstanceFieldname,
					resolveInfo)) {
				graph.connectNodes(producers, saveServiceInstaceNode);
			}
		}
	}

	/**
	 * Adds sender and receiver nodes to enable the return of calculated fields and
	 * of service handles.
	 */
	public void addSendToClientNodes(ExecutorHandle executor) {
		Graph graph = getGraphFor(executor);
		Set<String> producedFieldnames = new HashSet<>();
		/*
		 * Collect all fieldnames that are produced by this graph:
		 */
		for (String producedFieldname : GraphTraversal.iterateProducedFieldnames(graph, resolveInfo)) {
			if (!resolveInfo.getInputInformation().isInputField(producedFieldname)) {
				if (resolveInfo.getResolvePolicy().isToReturn(producedFieldname)) {
					producedFieldnames.add(producedFieldname);
				}
			}
		}
		/*
		 * Add sender node to the given graph and connect every producer node to the
		 * added node. Add receiving node to the client graph.
		 */
		String clientAddress = resolveInfo.getClientInfo().getClientHostAddress();
		Graph clientGraph = getClientGraph();
		for (String producedFieldname : producedFieldnames) {
			SendDataNode executorSend = new SendDataNode(producedFieldname, clientAddress);
			ReceiveDataNode clientReceive = new ReceiveDataNode(producedFieldname);
			clientGraph.addNode(clientReceive);
			graph.addNode(executorSend);
			for (BaseNode bn : GraphTraversal.iterateNodesProducingFieldname(graph, producedFieldname, resolveInfo)) {
				graph.connectNodes(bn, executorSend);
			}
		}
	}

	/**
	 * Forwards the unresolved list of instructions to other gateways to be
	 * resolved.
	 */
	public void forwardUnresolvedNodes() {
		if (!unresolvedInstructionNodes.isEmpty()) {
			throw new GraphFormException(
					"Unsupported composition. Currenctly the forwarding to another gateway is not supported.");
		}
	}

	/**
	 * constructs a path from producer to consumer in the given graph to indicate
	 * the dependency. For now it simply adds an edge to the graph. TODO check if
	 * the method signature and provided data type match. Add marshal nodes.
	 */
	private void connectDataReceiver(Graph graph, BaseNode producer, BaseNode consumer) {
		graph.connectNodes(producer, consumer);
	}

	private Graph getGraphFor(ExecutorHandle executor) {
		if (!graphs.containsKey(executor)) {
			throw new RuntimeException("No graph for the given executorhandle defined.");
		}
		return graphs.get(executor);
	}

	private Graph getClientGraph() {
		return this.graphs.get(resolveInfo.getClientInfo().getClientExecutor());
	}

	/**
	 * Returns a map which maps each node to its priority of execution. -2 has no
	 * priority at all. -1 has client side priority. 0 <= i has the same priority as
	 * the i'th instruction.
	 * 
	 * A node with priority i means that its execution cannot depend on a node with
	 * priority above i. Priorities are derived from the index of the instruction in
	 * the fm-composition it originates from.
	 */
	private DefaultMap<BaseNode, Integer> createNodePriorityMap() {

		List<BaseNode> orderedInstructionList = GraphTraversal.topologicalSort(orderOfExecutionGraph);
		DefaultMap<BaseNode, Integer> nodePriority = new DefaultMap<>(() -> -2);
		for (BaseNode bn : GraphTraversal.iterateNodes(getClientGraph())) {
			nodePriority.put(bn, -1);
		}

		/*
		 * Calculate the priority based on the complete graph:
		 */
		Graph completeGraph = createCompleteGraph(true);

		/*
		 * Mark the subtree of the i'th instruction to be of at least the priority i.
		 * (priority is the index of the instruction)
		 */
		for (int priority = 0, instuctionCount = orderedInstructionList
				.size(); priority < instuctionCount; priority++) {

			BaseNode instructionSource = orderedInstructionList.get(priority);
			nodePriority.put(instructionSource, priority);

			for (BaseNode instructionChild : GraphTraversal.BFS(completeGraph, instructionSource)) {
				nodePriority.put(instructionChild, priority);
			}
		}

		return nodePriority;
	}

	/**
	 * Returns a new graph which merges every graph from the 'graphs' map. If
	 * withSequentialInstructions is true the orderOfExecutionGraph is also merged.
	 */
	private Graph createCompleteGraph(boolean withSequentialInstructions) {
		Graph completedGraph = new Graph();
		for (ExecutorHandle eh : graphs.keySet()) {
			completedGraph.copyFrom(getGraphFor(eh));
		}
		if (withSequentialInstructions) {
			completedGraph.copyFrom(orderOfExecutionGraph);
		}
		return completedGraph;
	}

}
