package de.upb.sede.core;

import de.upb.sede.util.JsonSerializable;
import de.upb.sede.util.Streams;
import org.json.simple.JSONObject;

import java.util.*;
import java.util.regex.Pattern;

public abstract class SEDEObject implements JsonSerializable {


	/**
	 * Regex for a class path.
	 */
	private final static String REGEX_real_type = "^(?:(?:(?:[_a-zA-Z]\\w*+)\\.)++(?:(?:[_a-zA-Z]\\w*+)))$";
	private final static Pattern PATTERN_real_type = Pattern.compile(REGEX_real_type);


	private String type;

	/**
	 * Base Constructor which is in turn used by all the other constructors.
	 *
	 * @param type   Type of the data.
	 */
	public SEDEObject(String type) {
		this.type = Objects.requireNonNull(type);
	}


	public abstract <T> T getDataField();

	public String getType() {
		return type;
	}

	public static boolean isPrimitive(String type) {
		for (PrimitiveDataField.PrimitiveType primitivType : PrimitiveDataField.PrimitiveType.values()) {
			if (type.equalsIgnoreCase(primitivType.toString())) {
				return true;
			}
		}
		/**
		 * matched none of the primitive types.
		 */
		return false;
	}

	public static boolean isServiceInstanceHandle(String type) {
		return type.toLowerCase().startsWith("serviceinstance");
	}

	public static boolean isReal(String type) {
		return PATTERN_real_type.matcher(type).matches();
	}

	public static boolean isSemantic(String type) {
		return !isPrimitive(type) && !isServiceInstanceHandle(type) && !isReal(type);
	}

	/**
	 * @return True if the sede object is primitive typed.
	 */
	public boolean isPrimitive() {
		return false;
	}

	/**
	 * @return True if the sede object contains complex data as real java objects.
	 */
	public boolean isReal() {
		return false;
	}

	/**
	 * @return True if the sede object is a service instance handle.
	 * If so {@link SEDEObject#getServiceHandle()}  can be used.
	 *
	 */
	public boolean isServiceInstanceHandle() {
		return false;
	}

	/**
	 * @return True if the sede object is a service instance handle that actually contains a service instance object.
	 * If so {@link SEDEObject#getServiceInstance()} can be used.
	 */
	public boolean isServiceInstance() {
		return false;
	}

	/**
	 *
	 * @return True if the sede object is of semantic type, which means that it encapsulates an inputstream of bytes.
	 */
	public boolean isSemantic() {
		return false;
	}

	/**
	 * @return String representation of this Sede object.
	 */
	public String toString() {
		return getType() + " - ";
	}


	/**
	 * Returns the serviceinstancehandle if the sede object holds one.
	 * Else it will throw an exception.
	 *
	 * @return Service instance handle of this sede object.
	 */
	public ServiceInstanceHandle getServiceHandle() {
		if (!isServiceInstanceHandle()) {
			throw new RuntimeException("Trying to access a service instance although SEDEObject holds object of type: \"" + getType() + "\"");
		} else {
			return ((ServiceInstanceField)this).getDataField();
		}
	}

	/**
	 * If this sede object holds a service instance it will try to parse the inner service instance to the given generic type and return it.
	 *
	 * @param <T> Type of the inner service.
	 * @return Service instance.
	 */
	public <T> T getServiceInstance() {
		return (T) (getServiceHandle().getServiceInstance().get());
	}


	@Override
	public JSONObject toJson() {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("type", getType());
		return jsonResult;
	}

	@Override
	public void fromJson(Map<String, Object> jsonData) {
		this.type = (String) jsonData.get("type");
	}

	public static SEDEObject constructFromJson(Map<String, Object> jsonData) {
		String type = Objects.requireNonNull((String) jsonData.get("type"));
		Object data = jsonData.get("data");
		if(isPrimitive(type)) {
			return new PrimitiveDataField(type, data);
		} else if(isServiceInstanceHandle(type)) {
			ServiceInstanceHandle serviceInstanceHandle = new ServiceInstanceHandle();
			serviceInstanceHandle.fromJson((Map<String, Object>) data);
			return new ServiceInstanceField(serviceInstanceHandle);
		} else if(isSemantic(type)) {
			SemanticDataField dataField = new SemanticDataField(type, Streams.EmptyInStream(), true);
			dataField.fromJson(jsonData);
			return dataField;
		} else {
			throw new RuntimeException("cannot parse real data from json. Use semantic streamer instead: " + type);
		}
	}
}
