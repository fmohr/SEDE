package de.upb.sede.composition.graphs;

import java.util.Objects;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ResolvePolicy;

class ReceiveDataNode extends BaseNode{
	
	private final String fieldname;
	
	ReceiveDataNode(String fieldname){
		this.fieldname = Objects.requireNonNull(fieldname);
	}
	
	@Override
	boolean changesState(String fieldname, ClassesConfig configuration) {
		return fieldname.equals(this.fieldname);
	}

	@Override
	void expand(GraphComposition graph, ClassesConfig configuration, ResolvePolicy policy) {
		/* does no expanding */
	}

}
