package ai.services.core;

import org.json.simple.JSONObject;

import java.util.Map;

public class ObjectDataField extends SEDEObject{

	private Object object;

	public ObjectDataField(String type, Object object) {
		super(type);
		this.object = object;
	}

	/**
	 * Only use in tests, please.
	 * Same as: ObjectDataField(realData.getClass().getName(), realData)
	 *
	 * @param realData some java object whose class name will be used as the type.
	 *                 Note that in general the actual type doesnt have to correspond with the class name in java.
	 */
	public ObjectDataField(Object realData) {
		this(realData.getClass().getName(), realData);
	}


	public boolean isReal() {
		return true;
	}

	@Override
	public <T> T getDataField() {
		return (T) object;
	}

	@Override
	public JSONObject toJson() {
		throw new RuntimeException("Cannot cast object data field to json without caster information. " +
				"Use the semantic streamer instead.");
	}

	@Override
	public void fromJson(Map<String, Object> jsonData) {
		throw new RuntimeException("Cannot cast object data field from json without caster information. " +
				"Use the semantic streamer instead.");
	}


	/**
	 * @return String representation
	 */
	public String toString() {
		return super.toString() + object.getClass().getSimpleName();
	}

}
