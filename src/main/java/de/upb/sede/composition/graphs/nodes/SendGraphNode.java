package de.upb.sede.composition.graphs.nodes;

import java.util.Map;

public class SendGraphNode extends BaseNode {

	private final String graph;

	private final Object contactInfo;

	public SendGraphNode(String graph, Object contactInfo) {
		super();
		this.graph = graph;
		this.contactInfo = contactInfo;
	}

	public String getGraph() {
		return graph;
	}


	public String toString() {
		return "send graph";
	}

	public Object getContactInfo() {
		return contactInfo;
	}
}
