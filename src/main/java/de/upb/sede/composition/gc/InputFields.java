package de.upb.sede.composition.gc;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * Sent by the user.
 *
 */
public class InputFields {
	private final Map<String, String> fieldTypes;
	private final Map<String, ServiceInstanceHandle> serviceInstanceMap;
	
	public InputFields(Map<String, String> fieldTypes, Map<String, ServiceInstanceHandle> serviceInstanceMap) {
		this.fieldTypes = fieldTypes;
		this.serviceInstanceMap = serviceInstanceMap;
	}
	
	public boolean isInputField(String fieldname) {
		return fieldTypes.containsKey(Objects.requireNonNull(fieldname));
	}
	public String getInputFieldType(String fieldname) {
		return fieldTypes.get(Objects.requireNonNull(fieldname));
	}


	public boolean isServiceInstanceHandle(String fieldname) {
		return serviceInstanceMap.containsKey(fieldname);
	}
	public ServiceInstanceHandle getServiceInstanceHandle(String fieldname) {
		return serviceInstanceMap.get(fieldname);
	}

	public Collection<String> getInputFields() {
		return fieldTypes.keySet();
	}
}
