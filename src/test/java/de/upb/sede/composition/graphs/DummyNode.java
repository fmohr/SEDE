package de.upb.sede.composition.graphs;

import java.util.Collection;

import de.upb.sede.composition.gc.ResolveInformation;
import de.upb.sede.composition.graphs.nodes.BaseNode;

class DummyNode extends BaseNode {
	
	String name = "noname";
	DummyNode(){
		super();
	}
	
	DummyNode(String name){
		this.name = name;
	}

	@Override
	public
	boolean producesField(String fieldname, ResolveInformation resolveInfo) {
		return false;
	}

	@Override
	public
	Collection<String> consumingFields(ResolveInformation resolveInfo) {
		return null;
	}
	
	public String toString() {
		return name;
	}

	@Override
	public
	Collection<String> producingFields(ResolveInformation resolveInfo) {
		return null;
	}

}
