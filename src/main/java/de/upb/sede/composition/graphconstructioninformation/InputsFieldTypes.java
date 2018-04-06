package de.upb.sede.composition.graphconstructioninformation;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Send by the user.
 * @author aminfaez
 *
 */
public class InputsFieldTypes {
	private Map<String, String> fieldTypes;
	
	public InputsFieldTypes(Map<String, String> fieldTypes) {
		this.fieldTypes = Collections.unmodifiableMap(fieldTypes);
	}
	
	public boolean isInputField(String fieldname) {
		return fieldTypes.containsKey(Objects.requireNonNull(fieldname));
	}
	public String getInputFieldType(String fieldname) {
		return fieldTypes.get(Objects.requireNonNull(fieldname));
	}
}
