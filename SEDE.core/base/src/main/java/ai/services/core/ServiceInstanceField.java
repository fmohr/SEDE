package ai.services.core;

import org.json.simple.JSONObject;

import java.util.Map;

public class ServiceInstanceField extends SEDEObject {

	private ServiceInstanceHandle handle;

	public final static String SERVICE_INSTANCE_HANDLE_TYPE = ServiceInstanceHandle.class.getSimpleName();

	public ServiceInstanceField(ServiceInstanceHandle handle) {
		super(SERVICE_INSTANCE_HANDLE_TYPE);
		this.handle = handle;
	}

	@Override
	public ServiceInstanceHandle getDataField() {
		return handle;
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

	public boolean isServiceInstanceHandle(){
		return true;
	}

	public boolean isServiceInstance() {
		return getDataField().getServiceInstance().isPresent();
	}

	/**
	 * @return String representation of this Sede object.
	 */
	public String toString() {
		return super.toString() + handle.toString() + (isServiceInstance()? " with service":"");
	}

}
