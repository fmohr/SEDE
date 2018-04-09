package de.upb.sede.composition.gc;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Sent by the user.
 * @author aminfaez
 *
 */
public class InputFields {
	private Map<String, String> fieldTypes;
	
	public InputFields(Map<String, String> fieldTypes) {
		this.fieldTypes = Collections.unmodifiableMap(fieldTypes);
	}
	
	public boolean isInputField(String fieldname) {
		return fieldTypes.containsKey(Objects.requireNonNull(fieldname));
	}
	public String getInputFieldType(String fieldname) {
		return fieldTypes.get(Objects.requireNonNull(fieldname));
	}


	public boolean isServiceInstanceHandle(String fieldname) {
		return false;
	}
	public ServiceInstanceHandle getServiceInstanceHandle(String fieldname) {
		return null;
	}

	public Collection<String> getInputFields() {
		return fieldTypes.keySet();
	}
}
