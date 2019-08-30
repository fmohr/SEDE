package de.upb.sede.composition.graphs.nodes;

import java.util.Map;

public class FinishNode extends BaseNode{

	private final Map<String, Object> contactInfo;

	private final String fieldname;

	public FinishNode(Map<String, Object> contactInfo, String fieldname) {
		this.contactInfo = contactInfo;
		this.fieldname = fieldname;
	}

	public Map<String, Object> getContactInfo() {
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
