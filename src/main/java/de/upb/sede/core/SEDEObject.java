package de.upb.sede.core;

import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.ServiceInstance;
import org.json.simple.JSONObject;

import java.util.Objects;
import java.util.regex.Pattern;

public class SEDEObject {
	public static enum PrimitiveType {
		NULL, String, Number, Bool;

		public static PrimitiveType insensitiveValueOf(String searchName){
			for(PrimitiveType type : PrimitiveType.values()){
				if(type.name().equalsIgnoreCase(searchName)){
					return type;
				}
			}
			throw new RuntimeException("BUG: search name: " + searchName);
		}
	}

	public final static String SERVICE_INSTANCE_HANDLE_TYPE = ServiceInstanceHandle.class.getSimpleName();


	/** Regex for a class path. */
	private final static String REGEX_real_type = "^(?:(?:(?:[_a-zA-Z]\\w*+)\\.)++(?:(?:[_a-zA-Z]\\w*+)))$";
	private final static Pattern PATTERN_real_type = Pattern.compile(REGEX_real_type);


	private final String type;

	private final Object object;

	public SEDEObject(String type, Object object) {
		this.type = Objects.requireNonNull(type);
		this.object = Objects.requireNonNull(object);

		if(object instanceof ServiceInstanceHandle && !type.equalsIgnoreCase(SERVICE_INSTANCE_HANDLE_TYPE)){
			throw new RuntimeException("BUG: given object is service instance handle but t givenype is: " + type);
		}
		if(isSemantic(type) && !this.isSemantic()){
			throw new RuntimeException("BUG: given object is of semantic type but given data isn't a byte array: " + object.getClass());
		}
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

	public static boolean isPrimitive(String type){
		for(PrimitiveType primitivType : PrimitiveType.values()){
			if(type.equalsIgnoreCase(primitivType.toString())){
				return true;
			}
		}
		/**
		 * matched none of the primitive types.
		 */
		return false;
	}

	public boolean isPrimitive(){
		return isPrimitive(getType());
	}

	public static boolean isServiceInstanceHandle(String type){
		return type.equalsIgnoreCase(SERVICE_INSTANCE_HANDLE_TYPE);
	}


	public  boolean isServiceInstanceHandle(){
		return isServiceInstanceHandle(getType());
	}

	public boolean isServiceInstance(){
		return isServiceInstanceHandle() && ((ServiceInstanceHandle)object).getServiceInstance().isPresent();
	}

	public static boolean isReal(String type){
		return PATTERN_real_type.matcher(type).matches();
	}

	public boolean isReal(){
		return isReal(getType());
	}

	public static boolean isSemantic(String type){
		return !isPrimitive(type) && !isServiceInstanceHandle(type) && !isReal(type);
	}

	public boolean isSemantic(){
		return isSemantic(getType()) && object instanceof byte[];
	}

	public String toString(){
		return getType() + " - " + object == null ? "null" : object.getClass().getSimpleName();
	}


	/**
	 * Returns the serviceinstancehandle if the sede object holds one.
	 * Else it will throw an exception.
	 * @return Service instance handle of this sede object.
	 */
	public ServiceInstanceHandle getServiceHandle() {
		if(!isServiceInstance()) {
			throw new RuntimeException("Trying to access a service instance although SEDEObject holds object of type: \"" + getType() + "\"");
		} else {
			return  ((ServiceInstanceHandle)object);
		}
	}

	/**
	 * If this sede object holds a service instance it will try to parse the inner service instance to the given generic type and return it.
	 *
	 * @param <T> Type of the inner service.
	 * @return Service instance.
	 */
	public <T>T getServiceInstance() {
		return (T) (getServiceHandle().getServiceInstance().get());
	}
}
