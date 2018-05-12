package de.upb.sede.composition.graphs.nodes;

import java.util.*;

public class TransmitDataNode extends BaseNode {

	private final String fieldname;

	private final Map<String, String> contactInfo;

	private final String caster;

	private final String semanticTypename;

	public TransmitDataNode(String fieldname, Map<String, String>  contactInfo, String caster, String semanticTypename) {
		this.fieldname = Objects.requireNonNull(fieldname);
		this.contactInfo = Objects.requireNonNull(contactInfo);
		this.caster = Objects.requireNonNull(caster);
		this.semanticTypename = Objects.requireNonNull(semanticTypename);
	}

	public static TransmitDataNode rawTransmit(String fieldname, Map<String, String>  contactInfo){
		return new TransmitDataNode(fieldname, contactInfo, "raw", "raw");
	}

	public Map<String, String>  getContactInfo() {
		return contactInfo;
	}

	public String getSendingFieldName() {
		return fieldname;
	}

	public String getCaster() {
		return caster;
	}

	public String getSemanticTypename() {
		return semanticTypename;
	}

	public String toString() {
		return "transmit \"" + fieldname + "\"->" + contactInfo.get("id");
	}
}
