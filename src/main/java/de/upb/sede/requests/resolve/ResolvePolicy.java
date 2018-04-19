package de.upb.sede.requests.resolve;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.exceptions.BadResolveRequest;
import de.upb.sede.util.JsonSerializable;

/**
 * 
 * @author aminfaez
 *
 */
public class ResolvePolicy implements JsonSerializable {

	/*
	 * list flags
	 */
	private static final String all = "ALL";
	private static final String none = "None";
	private static final String listed = "Listed";

	private String returnPolicy;

	private String servicePolicy;

	private List<String> returnFieldnames;

	private List<String> persistentServices;

	public ResolvePolicy() {
		setStandardPolicy();
	}

	public String getReturnPolicy() {
		return returnPolicy;
	}

	private void setReturnPolicy(String returnPolicy) {
		Objects.requireNonNull(returnPolicy);
		if (returnPolicy.equalsIgnoreCase(all) || returnPolicy.equalsIgnoreCase(none)
				|| returnPolicy.equalsIgnoreCase(listed)) {
			this.returnPolicy = returnPolicy;
		} else {
			throw new BadResolveRequest("Not recognizable return policy: " + returnPolicy);
		}
	}

	public String getServicePolicy() {
		return servicePolicy;
	}

	private void setServicePolicy(String servicePolicy) {
		Objects.requireNonNull(servicePolicy);
		if (servicePolicy.equalsIgnoreCase(all) || servicePolicy.equalsIgnoreCase(none)
				|| servicePolicy.equalsIgnoreCase(listed)) {
			this.servicePolicy = servicePolicy;
		} else {
			throw new BadResolveRequest("Not recognizable service policy: " + servicePolicy);
		}
	}

	public List<String> getReturnFieldnames() {
		return returnFieldnames;
	}

	private void setReturnFieldnames(List<String> returnFieldnames) {
		this.returnFieldnames = returnFieldnames;
	}

	public List<String> getPersistentServices() {
		return persistentServices;
	}

	private void setPersistentServices(List<String> persistentServices) {
		this.persistentServices = persistentServices;
	}

	private void setStandardPolicy() {
		setReturnPolicy(all);
		setServicePolicy(all);
	}

	public boolean isToReturn(String fieldName) {
		if (returnPolicy.equalsIgnoreCase(all)) {
			return true;
		}
		if (returnPolicy.equalsIgnoreCase(none)) {
			return false;
		} else if (returnPolicy.equalsIgnoreCase(listed)) {
			return (returnFieldnames != null && returnFieldnames.contains(fieldName));
		} else {
			throw new BadResolveRequest("Not recognizable return policy: " + returnPolicy);
		}
	}

	public boolean isPersistentService(String serviceInstanceFieldName) {
		if (servicePolicy.equalsIgnoreCase(all)) {
			return true;
		}
		if (servicePolicy.equalsIgnoreCase(none)) {
			return false;
		} else if (servicePolicy.equalsIgnoreCase(listed)) {
			return (returnFieldnames != null && returnFieldnames.contains(serviceInstanceFieldName));
		} else {
			throw new BadResolveRequest("Not recognizable return policy: " + servicePolicy);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("return-policy", getReturnPolicy());
		jsonObject.put("service-policy", getServicePolicy());
		JSONArray persistantServices = new JSONArray();
		persistantServices.addAll(getPersistentServices());
		JSONArray returnFieldnames = new JSONArray();
		persistantServices.addAll(getReturnFieldnames());
		jsonObject.put("persistent-services", persistantServices);
		jsonObject.put("return-fieldnames", returnFieldnames);
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		
		this.setReturnPolicy((String) data.get("return-policy"));
		this.setServicePolicy((String) data.get("service-policy"));
		
		this.setPersistentServices((List<String>) data.get("persistent-service"));
		this.setReturnFieldnames((List<String>) data.get("return-fieldnames"));
		
	}
}
