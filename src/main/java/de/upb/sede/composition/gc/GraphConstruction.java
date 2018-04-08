package de.upb.sede.composition.gc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.upb.sede.composition.graphs.Graph;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.config.ClassesConfig;

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
	

	private void resolveContext(InstructionNode instNode) {
		String serviceClass = instNode.getServiceClass(resolveInfo);
		boolean resolvedContext = false; /* flag that indicates that the node has been added to the graphs map and therefore resolved */
		
		if(resolveInfo.getClassesConfig().classknown(serviceClass)) {
			/*
			 * class is known. Try to find an executor that knows the service class:
			 */
			List<ExecutorHandle> supportingExecutor = resolveInfo.getExecutorCoordinator().executorsSupportingServiceClass(serviceClass);
			if(!supportingExecutor.isEmpty()) {
				/*
				 * There is at least one executor that can carry out the instruction.
				 */
				/*
				 * Try to pick an executor that was already selected for another instruction. 
				 */
				for(ExecutorHandle eh : supportingExecutor) {
					if(graphs.containsKey(eh)) {
						graphs.get(eh).addNode(instNode);
						resolvedContext = true;
						break;
					}
				}
				if(!resolvedContext) {
					/*
					 * No executor in the supportingExecutor list was found that is already assigned to do another instruction.
					 * Pick a new random executor and create a new graph for it. TODO do better scheduling:
					 */
					int randomIndex = (int) (supportingExecutor.size() * Math.random());
					ExecutorHandle randomExecutor = supportingExecutor.get(randomIndex);
					Graph newExecutorGraph = new Graph();
					newExecutorGraph.addNode(instNode);
					graphs.put(randomExecutor, newExecutorGraph);
				}
			}
			
		}
		if(!resolvedContext) {
			/*
			 * Context of the given node cannot be resolved. Put it into the unresolved list:
			 */
			unresolvedInstructionNodes.add(instNode);
		}
	}

	/**
	 * 
	 */
	public void resolveDataFlowDependency() {
	}

}
