package de.upb.sede.core;

import org.json.simple.JSONObject;

import java.util.Map;
import java.util.Objects;

public class PrimitiveDataField extends SEDEObject {

    private Object primitiveObject;


	private PrimitiveDataField(PrimitiveType type, Object primitiveObject) {
		super(type.name());
		this.primitiveObject = primitiveObject;
	}

	public PrimitiveDataField(String type, Object primitiveObject) {
		this(PrimitiveType.insensitiveValueOf(type).orElseThrow(() -> new RuntimeException("Primitive " + type + " not found.")), primitiveObject);
	}

	public PrimitiveDataField(Number number) {
		this(PrimitiveType.Number, number);
	}

	public PrimitiveDataField(String string) {
		this(PrimitiveType.String, string);
	}

	public PrimitiveDataField(Boolean bool) {
		this(PrimitiveType.Bool, bool);
	}

	public PrimitiveDataField() {
		this(PrimitiveType.NULL, null);
	}

	public boolean isPrimitive() {
		return true;
	}

	@Override
	public <T> T getDataField() {
		return (T) primitiveObject;
	}

	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("data", getDataField());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> jsonData) {
		super.fromJson(jsonData);
		primitiveObject = jsonData.get("data");
	}

	/**
	 * @return String representation
	 */
	public String toString() {
		return super.toString() + primitiveObject.toString();
	}

}
