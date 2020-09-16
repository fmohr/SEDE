package ai.services.core;

import org.json.simple.JSONObject;

import java.util.Map;

public class PrimitiveDataField extends SEDEObject {

    private Object primitiveObject;


	public PrimitiveDataField(Primitives type, Object primitiveObject) {
		super(type.name());
		this.primitiveObject = primitiveObject;
	}

	public PrimitiveDataField(String type, Object primitiveObject) {
		this(Primitives.insensitiveValueOf(type).orElseThrow(() -> new RuntimeException("Primitive " + type + " not found.")), primitiveObject);
	}

	public PrimitiveDataField(Number number) {
		this(Primitives.Number, number);
	}

	public PrimitiveDataField(String string) {
		this(Primitives.String, string);
	}

	public PrimitiveDataField(Boolean bool) {
		this(Primitives.Bool, bool);
	}

	public PrimitiveDataField() {
		this(Primitives.NULL, null);
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
