package de.upb.sede.composition.graphs;

import java.util.*;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.SendGraphNode;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.gateway.ResolveInfo;

public class GraphConstruction {

	private final DataFlowAnalysis dataFlow;

	public final static GraphConstruction constructFromFMComp(String fmComposition, ResolveInfo resolveInformation) {

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
		
		gc.calcResolvedClientGraph();
		return gc;
	}

	private void calcResolvedClientGraph() {
		CompositionGraph resolvedClientGraph = dataFlow.getClientExecPlan().getGraph();
//		/*
//		 * prioritize executions on the client node:
//		 */
//		List<BaseNode> sortedNodes = GraphTraversal.topologicalSort(resolvedClientGraph);
//		for(int i = 0, size = sortedNodes.size(); i < size-1; i++) {
//			resolvedClientGraph.connectNodes(sortedNodes.get(i), sortedNodes.get(i+1));
//		}
		/*
		 * Add send graph nodes to the client graph and returns it:
		 */
		GraphJsonSerializer gjs = new GraphJsonSerializer();
		for (ExecPlan exec : dataFlow.getInvolvedExecutions()) {
			if(exec == dataFlow.getClientExecPlan()) {
				continue;
			}
			String jsonGraph = gjs.toJson(exec.getGraph()).toJSONString();
			SendGraphNode sendGraph = new SendGraphNode(jsonGraph, exec.getExecutor().getContactInfo());
			resolvedClientGraph.addNode(sendGraph);
		}
//		for(DependencyEdge transmitEdge : GraphTraversal.iterateEdges(getTransmissionGraph())) {
//			if(resolvedClientGraph.contains(transmitEdge.getFrom())) {
//				resolvedClientGraph.connectNodes(oldNode, transmitEdge.getFrom());
//				oldNode = transmitEdge.getFrom();
//			}
//		}
//		for(DependencyEdge transmitEdge : GraphTraversal.iterateEdges(getTransmissionGraph())) {
//			if(resolvedClientGraph.contains(transmitEdge.getTo())) {
//				resolvedClientGraph.connectNodes(oldNode, transmitEdge.getTo());
//				oldNode = transmitEdge.getTo();
//			}
//		}
	}

	
	
	private GraphConstruction(ResolveInfo resolveInfo, List<InstructionNode> instructions) {
		this.dataFlow = new DataFlowAnalysis(resolveInfo, instructions);
	}


	public List<ExecPlan> getExecutions() {
		return dataFlow.getInvolvedExecutions();
	}

	public CompositionGraph getClientGraph() {
		return dataFlow.getClientExecPlan().getGraph();
	}

	public CompositionGraph getTransmissionGraph() {
		return dataFlow.getTransmissionGraph();
	}

	public List<String> getReturnFields() {
		return dataFlow.getReturnFieldnames();
	}

	public Map<String, CompositionGraph> getInvolvedExecutions() {
		Map<String, CompositionGraph> comps = new HashMap<>();
		String clientId = dataFlow.getClientExecPlan().getExecutor().getExecutorId();
		comps.put(clientId, dataFlow.getClientExecPlan().getGraph());
		for(ExecPlan exec : getExecutions()) {
			if(exec.getExecutor().getExecutorId().equals(clientId)) {
				continue;
			}
			comps.put(exec.getExecutor().getExecutorId(), exec.getGraph());
		}
		return comps;
	}
}
