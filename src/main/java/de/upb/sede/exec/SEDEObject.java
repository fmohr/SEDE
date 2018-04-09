package de.upb.sede.exec;

public class SEDEObject {
	private final String type;
	private final Object object;

	public SEDEObject(String type, Object object) {
		this.type = type;
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	public String getType() {
		return type;
	}
}
