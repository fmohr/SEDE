package de.upb.sede.requests;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.util.JsonSerializable;

public class ExecutorRegistration implements JsonSerializable {

	private Optional<Map<String, String>> contactInformation = Optional.empty();


	private Optional<List<String>> capabilities = Optional.empty();

	private Optional<List<String>> supportedServices = Optional.empty();

	public ExecutorRegistration() {
	}

	public ExecutorRegistration(Map<String, String> contactInfo, List<String> capabilities, List<String> supportedServices) {
		super();
		setContactInformation(contactInfo);
		setCapabilities(capabilities);
		setSupportedServices(supportedServices);
	}

	public static ExecutorRegistration client_registration(String clientId) {
		Map<String, String> contactInfo = new HashMap<>();
		contactInfo.put("id", clientId);
		return new ExecutorRegistration(contactInfo, Collections.EMPTY_LIST, Collections.EMPTY_LIST);
	}

	/**
	 * @return the executor host address
	 */
	public String getId() {
		return contactInformation.get().get("id");
	}

	/**
	 * @return the executor contact information.
	 */
	public Map<String, String> getContactInfo() {
		return contactInformation.get();
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



	public void setContactInformation(Map<String, String> contactInformation) {
		this.contactInformation = Optional.of(contactInformation);
	}

	public void setCapabilities(List<String> capabilities) {
		this.capabilities = Optional.of(Optional.ofNullable(capabilities).orElse(Collections.EMPTY_LIST));
	}

	public void setSupportedServices(List<String> supportedServices) {
		this.supportedServices = Optional.of(Optional.ofNullable(supportedServices).orElse(Collections.EMPTY_LIST));
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("contact-info", getContactInfo());
		jsonObject.put("capabilities", getCapabilities());
		jsonObject.put("supported-services", getSupportedServices());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		setContactInformation((Map<String, String> )data.get("contact-info"));
		setCapabilities((List<String>) data.get("capabilities"));
		setSupportedServices((List<String>) data.get("supported-services"));
	}
}