package de.upb.sede.composition.graphs.nodes;

import java.util.Map;

public class FinishNode extends BaseNode{

	private final Map<String, String> contactInfo;

	private final String fieldname;

	public FinishNode(Map<String, String> contactInfo, String fieldname) {
		this.contactInfo = contactInfo;
		this.fieldname = fieldname;
	}

	public Map<String, String> getContactInfo() {
		return contactInfo;
	}

	public String getFieldname() {
		return fieldname;
	}

	@Override
	public String toString() {
		return "FinishNode " + fieldname;
	}
}
