package de.upb.sede.composition.graphs;

import java.util.Collection;
import java.util.Objects;

import de.upb.sede.composition.graphconstructioninformation.ClassesConfig;

class ReceiveDataNode extends BaseNode{
	
	private final String fieldname;
	
	ReceiveDataNode(String fieldname){
		this.fieldname = Objects.requireNonNull(fieldname);
	} 
	
	@Override
	boolean producesField(String fieldname, ClassesConfig configuration) {
		return fieldname.equals(this.fieldname);
	}

	@Override
	Collection<String> consumingFields() {
		return null;
	}

}