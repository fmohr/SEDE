package de.upb.sede.composition.graphs.nodes;

public class InterruptExecNode extends BaseNode{

	private final Object contactInfo;

	public InterruptExecNode(Object contactInfo) {
		super();
		this.contactInfo = contactInfo;
	}

	public String toString() {
		return "interrupt exec";
	}

	public Object getContactInfo() {
		return contactInfo;
	}

}
