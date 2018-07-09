package de.upb.sede.composition.graphs;

import java.util.Collection;

import de.upb.sede.composition.graphs.nodes.BaseNode;

public class DummyNode extends BaseNode {

	String name = "noname";

	private Collection<String> producingFields;
	private Collection<String> consumingFields;

	DummyNode() {
		super();
	}

	public DummyNode(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

}
