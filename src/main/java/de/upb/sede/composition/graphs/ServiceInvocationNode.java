package de.upb.sede.composition.graphs;

public class ServiceInvocationNode extends InstructionNode {


	public ServiceInvocationNode(String basedOnInstruction, String context, String method) {
		super(basedOnInstruction, context, method);
	}


	@Override
	void expand(GraphComposition graph) {
		super.expand(graph);
		
	}
}
