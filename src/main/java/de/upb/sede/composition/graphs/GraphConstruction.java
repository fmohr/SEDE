package de.upb.sede.composition.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.SendGraphNode;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.gateway.ResolveInfo;
import de.upb.sede.util.FilteredIterator;

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
		CompositionGraph resolvedClientGraph = dataFlow.getClientExecution().getGraph();
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
		for (Execution exec : dataFlow.getInvolvedExecutions()) {
			if(exec == dataFlow.getClientExecution()) {
				continue;
			}
			String jsonGraph = gjs.toJson(exec.getGraph()).toJSONString();
			SendGraphNode sendGraph = new SendGraphNode(jsonGraph, exec.getExecutor().getHostAddress());
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


	public List<Execution> getExecutions() {
		return dataFlow.getInvolvedExecutions();
	}

	public CompositionGraph getResolvedClientGraph() {
		return dataFlow.getClientExecution().getGraph();
	}

	CompositionGraph getTransmissionGraph() {
		return dataFlow.getTransmissionGraph();
	}
}
