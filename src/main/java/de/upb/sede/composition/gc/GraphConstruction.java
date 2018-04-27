package de.upb.sede.composition.gc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.SendGraphNode;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;

public class GraphConstruction {

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
		
		gc.calcResolvedClientGraph();
		return gc;
	}

	public void calcResolvedClientGraph() {
		/*
		 * Add send graph nodes to the client graph and returns it:
		 */
		CompositionGraph resolvedClientGraph = dataFlow.getClientExecution().getGraph();
		GraphJsonSerializer gjs = new GraphJsonSerializer();
		for (Execution exec : dataFlow.getInvolvedExecutions()) {
			if(exec == dataFlow.getClientExecution()) {
				continue;
			}
			String jsonGraph = gjs.toJson(exec.getGraph()).toJSONString();
			SendGraphNode sendGraph = new SendGraphNode(jsonGraph, exec.getExecutor().getHostAddress());
			resolvedClientGraph.addNode(sendGraph);
		}
	}

	
	
	private GraphConstruction(ResolveInfo resolveInfo, List<InstructionNode> instructions) {
		this.resolveInfo = resolveInfo;

		this.dataFlow = new DataFlowAnalysis(resolveInfo, instructions);
	}


	public List<Execution> getExecutions() {
		return dataFlow.getInvolvedExecutions();
	}

	public CompositionGraph getResolvedClientGraph() {
		return dataFlow.getClientExecution().getGraph();
	}
}
