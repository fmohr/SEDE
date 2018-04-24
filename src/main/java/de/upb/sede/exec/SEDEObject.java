package de.upb.sede.exec;

import org.json.simple.JSONObject;

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

	public String toJSON() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("type", type);
		jsonObject.put("object", object);
		return jsonObject.toJSONString();
	}
}
