package de.upb.sede.composition.graphs.nodes;

import java.util.Objects;

public class AcceptDataNode extends BaseNode {

	private final String fieldname;

	public AcceptDataNode(String fieldname) {
		this.fieldname = Objects.requireNonNull(fieldname);
	}

	public String getReceivingFieldname() {
		return fieldname;
	}

	public String toString() {
		return "accept \"" + getReceivingFieldname() + "\"";
	}

}