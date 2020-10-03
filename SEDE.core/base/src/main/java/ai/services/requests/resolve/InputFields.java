package ai.services.requests.resolve;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ai.services.core.SEDEObject;
import org.json.simple.JSONObject;

import ai.services.core.ServiceInstanceHandle;
import ai.services.util.JsonSerializable;

/**
 * Sent by the user.
 *
 */
@Deprecated
public class

InputFields implements JsonSerializable {

	private Map<String, String> fieldTypes;
	private Map<String, ServiceInstanceHandle> serviceInstanceMap;

	public InputFields() {
		fieldTypes = Collections.EMPTY_MAP;
		serviceInstanceMap = Collections.EMPTY_MAP;
	}

	public InputFields(Map<String, String> fieldTypes, Map<String, ServiceInstanceHandle> serviceInstanceMap) {
		this.fieldTypes = fieldTypes;
		this.serviceInstanceMap = serviceInstanceMap;
	}

	public static InputFields fromMap(Map<String, SEDEObject> inputs){
		Map<String, String> inputTypes = new HashMap<>();
		Map<String, ServiceInstanceHandle> inputInstances = new HashMap<>();
		for (String fieldname : inputs.keySet()) {
			SEDEObject field = inputs.get(fieldname);
			inputTypes.put(fieldname, field.getType());
			if(field.isServiceInstanceHandle()) {
				inputInstances.put(fieldname, field.getServiceHandle());
			}
		}
		InputFields inputFields = new InputFields(inputTypes, inputInstances);
		return inputFields;
	}

	public boolean isInputField(String fieldname) {
		return fieldTypes.containsKey(Objects.requireNonNull(fieldname));
	}

	public String getInputFieldType(String fieldname) {
		return fieldTypes.get(Objects.requireNonNull(fieldname));
	}

	public boolean isServiceInstance(String fieldname) {
		return serviceInstanceMap.containsKey(fieldname);
	}

	public ServiceInstanceHandle getServiceInstanceHandle(String fieldname) {
		return serviceInstanceMap.get(fieldname);
	}

	public Collection<String> getInputFields() {
		return fieldTypes.keySet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();

		JSONObject jsonFieldTypes = new JSONObject();
		jsonFieldTypes.putAll(fieldTypes);
		jsonObject.put("field-types", jsonFieldTypes);

		JSONObject jsonServiceInstanceMap = new JSONObject();
		for (String fieldname : serviceInstanceMap.keySet()) {
			jsonServiceInstanceMap.put(fieldname, serviceInstanceMap.get(fieldname).toJson());
		}
		jsonObject.put("service-instance-map", jsonServiceInstanceMap);

		return jsonObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJson(Map<String, Object> data) {
		Map<String, String> jsonFieldTypes = (Map<String, String>) data.get("field-types");
		this.fieldTypes = new HashMap<>(jsonFieldTypes);

		this.serviceInstanceMap = new HashMap<>();
		Map<String, Map<String, Object>> jsonServiceInstanceMap = (Map<String, Map<String, Object>>) data
				.get("service-instance-map");
		for (String fieldname : jsonServiceInstanceMap.keySet()) {
			ServiceInstanceHandle serviceInstanceHandle = new ServiceInstanceHandle();
			serviceInstanceHandle.fromJson(jsonServiceInstanceMap.get(fieldname));
			serviceInstanceMap.put(fieldname, serviceInstanceHandle);
		}
	}

	public Iterable<String> iterateInputs() {
		return fieldTypes.keySet();
	}
}
