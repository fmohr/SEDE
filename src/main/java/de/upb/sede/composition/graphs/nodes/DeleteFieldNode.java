package de.upb.sede.composition.graphs.nodes;

public class DeleteFieldNode extends BaseNode {

	private final String fieldname;

	public String getFieldname() {
		return fieldname;
	}

	public DeleteFieldNode(String fieldname) {
		this.fieldname = fieldname;
	}

	public String toString() {
		return "del \"" + fieldname + "\"";
	}

}
