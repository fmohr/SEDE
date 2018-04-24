package de.upb.sede.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.util.JsonSerializable;

public class ExecutorRegistration implements JsonSerializable {

	private Optional<String> executorHost;

	private Optional<List<String>> capabilities;

	private Optional<List<String>> supportedServices;

	public ExecutorRegistration() {

	}

	public ExecutorRegistration(String myHost, List<String> capabilities, List<String> supportedServices) {
		super();
		this.executorHost = Optional.of(myHost);
		this.capabilities = Optional.of(capabilities);
		this.supportedServices = Optional.ofNullable(supportedServices);
	}

	/**
	 * @return the executor host address
	 */
	public String getHost() {
		return executorHost.get();
	}

	/**
	 * @return the capabilities
	 */
	public List<String> getCapabilities() {
		return capabilities.get();
	}

	/**
	 * @return the supportedServices
	 */
	public List<String> getSupportedServices() {
		return supportedServices.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("host", getHost());
		JSONArray jsonCapabilites = new JSONArray();
		jsonCapabilites.addAll(getCapabilities());
		jsonObject.put("capabilities", jsonCapabilites);
		JSONArray jsonSupportedServices = new JSONArray();
		jsonSupportedServices.addAll(getSupportedServices());
		jsonObject.put("supported-services", jsonSupportedServices);
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		executorHost = Optional.of((String) data.get("host"));
		capabilities = Optional.of(new ArrayList<>((List<String>) data.get("capabilities")));
		supportedServices = Optional.of(new ArrayList<>((List<String>) data.get("supported-services")));
	}

}