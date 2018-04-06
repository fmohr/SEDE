package de.upb.sede.composition.graphs;

import java.util.Collection;

import de.upb.sede.composition.graphconstructioninformation.ClassesConfig;

class DummyNode extends BaseNode {
	
	String name = "noname";
	DummyNode(){
		super();
	}
	
	DummyNode(String name){
		this.name = name;
	}

	@Override
	boolean producesField(String fieldname, ClassesConfig configuration) {
		return false;
	}

	@Override
	Collection<String> consumingFields() {
		return null;
	}
	
	public String toString() {
		return name;
	}

}
