package de.upb.sede.composition.graphs;

public class ServiceCreationNode extends InstructionNode {


	

	public ServiceCreationNode(String basedOnInstruction, String context, String method) {
		super(basedOnInstruction, context, method);
	}

	@Override
	void expand(GraphComposition graph) {
		super.expand(graph);
	}


}
