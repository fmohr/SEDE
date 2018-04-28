package de.upb.sede.composition.graphs;

import java.util.Collection;

import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.gateway.ResolveInfo;

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

	@Override
	public boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		return producingFields != null && producingFields.contains(fieldname);
	}

	@Override
	public Collection<String> consumingFields(ResolveInfo resolveInfo) {
		return consumingFields;
	}

	public String toString() {
		return name;
	}

	@Override
	public Collection<String> producingFields(ResolveInfo resolveInfo) {
		return producingFields;
	}

}
