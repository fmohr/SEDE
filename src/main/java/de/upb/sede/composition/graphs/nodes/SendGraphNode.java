package de.upb.sede.composition.graphs.nodes;

import java.util.Collection;
import java.util.Collections;

import de.upb.sede.gateway.ResolveInfo;

public class SendGraphNode extends BaseNode {

	private final String graph;

	private final String executorsAddress;

	public SendGraphNode(String graph, String executorsAddress) {
		super();
		this.graph = graph;
		this.executorsAddress = executorsAddress;
	}

	public String getGraph() {
		return graph;
	}

	public String getExecutorsAddress() {
		return executorsAddress;
	}

	@Override
	public boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		return false;
	}

	@Override
	public Collection<String> consumingFields(ResolveInfo resolveInfo) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public Collection<String> producingFields(ResolveInfo resolveInfo) {
		return Collections.EMPTY_LIST;
	}

	public String toString() {
		return "send graph -> " + executorsAddress;
	}
}
