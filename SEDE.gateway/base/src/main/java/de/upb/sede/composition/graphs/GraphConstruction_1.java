package de.upb.sede.composition.graphs;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.composition.ICompositionCompilation;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.composition.graphs.nodes.SendGraphNode;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.gateway.ResolveInfo;

import java.util.*;

public class GraphConstruction_1 {

	private final DataFlowAnalysis dataFlow;

	public final static GraphConstruction_1 constructFromFMComp(String fmComposition, ResolveInfo resolveInformation) {

		/*
		 * Parse the fm composition
		 */
		List<String> fmInstructionList = FMCompositionParser.separateInstructions(fmComposition);
		List<InstructionNode> instructionList = new ArrayList<>();
		for (String fmInstruction : fmInstructionList) {
			InstructionNode instruction = FMCompositionParser.parseInstruction(fmInstruction);
			instructionList.add(instruction);
		}
		GraphConstruction_1 gc = new GraphConstruction_1(resolveInformation, Collections.unmodifiableList(instructionList));

		return gc;
	}

	public final static GraphConstruction_1 constructFromCC(ICompositionCompilation cc, ResolveInfo_1 resolveInformation) {
        return new GraphConstruction_1(cc, resolveInformation);
    }

	private GraphConstruction_1(ICompositionCompilation cc, ResolveInfo_1 resolveInformation) {
		this.dataFlow = new DataFlowAnalysis_1(resolveInformation, cc);
		calcResolvedClientGraph();
	}

    private void calcResolvedClientGraph() {
        CompositionGraph resolvedClientGraph = dataFlow.getClientExecPlan().getGraph();
        /*
         * Create sendgraph nodes from the serialization of the other execution graphs:
         */
        List<BaseNode> sendGraphNodes = new ArrayList<>();
        GraphJsonSerializer gjs = new GraphJsonSerializer();
        for (ExecPlan exec : dataFlow.getInvolvedExecutions()) {
            if(exec == dataFlow.getClientExecPlan()) {
                continue;
            }
            String jsonGraph = gjs.toJson(exec.getGraph()).toJSONString();
            SendGraphNode sendGraph = SendGraphNode.builder()
                .graph(jsonGraph)
                .contactInfo(exec.getTarget().getContactInfo())
                .build();
            sendGraphNodes.add(sendGraph);
        }
		/*
			 Let the send graph node execute first all the others:
		 */
        resolvedClientGraph.executeFirst(sendGraphNodes);

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

		String clientId = dataFlow.getClientExecPlan()
            .getTarget()
            .getContactInfo()
            .getQualifier();

		/*
		 * collect the execs:
		 */
		comps.put(clientId, dataFlow.getClientExecPlan().getGraph());
		for(ExecPlan exec : getExecutions()) {
		    String hostExecutorId = exec.getTarget().getContactInfo().getQualifier();
			if(hostExecutorId.equals(clientId)) {
				continue; // exclude client
			}
			comps.put(hostExecutorId, exec.getGraph());
		}
		return comps;
	}
}
