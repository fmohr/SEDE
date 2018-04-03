package de.upb.sede.composition.graphs;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ResolvePolicy;

public class ServiceCreationNode extends InstructionNode {


	

	public ServiceCreationNode(int instructionIndex, String basedOnInstruction, String context, String method) {
		super(instructionIndex, basedOnInstruction, context, method);
	}

	
	@Override
	void expand(GraphComposition graph, ClassesConfig configuration, ResolvePolicy policy) {
		super.expand(graph, configuration, policy);
		
	}

	boolean changesState(String fieldname, ClassesConfig configuration) {
		return super.changesState(fieldname, configuration);
		
	}

}
