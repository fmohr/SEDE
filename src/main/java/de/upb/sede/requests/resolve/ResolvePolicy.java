package de.upb.sede.requests.resolve;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import de.upb.sede.exceptions.BadResolveRequest;
import de.upb.sede.util.JsonSerializable;

/**
 * 
 * @author aminfaez
 *
 */
public class ResolvePolicy implements JsonSerializable {
	
	private static final Logger log = LogManager.getLogger();

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

	private boolean blockTillFinished;

	public ResolvePolicy() {
		setStandardPolicy();
	}

	private void setStandardPolicy() {
		setReturnPolicy(all);
		setServicePolicy(all);
		setBlockTillFinished(true);
	}

	public boolean isToReturn(String fieldName) {
		if (returnPolicy.equalsIgnoreCase(all)) {
			return true;
		}
		if (returnPolicy.equalsIgnoreCase(none)) {
			return false;
		} else if (returnPolicy.equalsIgnoreCase(listed)) {
			return returnFieldnames.contains(fieldName);
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
			return persistentServices.contains(serviceInstanceFieldName);
		} else {
			throw new BadResolveRequest("Not recognizable return policy: " + servicePolicy);
		}
	}

	public String getReturnPolicy() {
		return returnPolicy;
	}

	public String getServicePolicy() {
		return servicePolicy;
	}

	private void setReturnPolicy(String returnPolicy) {
		Objects.requireNonNull(returnPolicy);
		if (returnPolicy.equalsIgnoreCase(all) || returnPolicy.equalsIgnoreCase(none)) {
			this.returnPolicy = returnPolicy;
			this.returnFieldnames = Collections.EMPTY_LIST;
		} else {
			throw new BadResolveRequest("Not recognizable return policy: " + returnPolicy);
		}
	}

	private void setServicePolicy(String servicePolicy) {
		Objects.requireNonNull(servicePolicy);
		if (servicePolicy.equalsIgnoreCase(all) || servicePolicy.equalsIgnoreCase(none)) {
			this.servicePolicy = servicePolicy;
			this.persistentServices = Collections.EMPTY_LIST;
		} else {
			throw new BadResolveRequest("Not recognizable service policy: " + servicePolicy);
		}
	}

	public List<String> getReturnFieldnames() {
		return Collections.unmodifiableList(returnFieldnames);
	}

	public List<String> getPersistentServices() {
		return Collections.unmodifiableList(persistentServices);
	}

	private void setReturnFieldnames(List<String> returnFieldnames) {
		this.returnFieldnames = Objects.requireNonNull(returnFieldnames);
		this.returnPolicy = listed;
	}

	private void setPersistentServices(List<String> persistentServices) {
		this.servicePolicy = listed;
		this.persistentServices = Objects.requireNonNull(persistentServices);
	}

	public boolean clientsideExecutionAllowed() {
		return true; // TODO do we need this to be turned off? note that the method isn't being used yet.
	}

	public boolean isBlockTillFinished() {
		return blockTillFinished;
	}

	public void setBlockTillFinished(boolean blockTillFinished) {
		this.blockTillFinished = blockTillFinished;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		if(getReturnPolicy().equals(listed)) {
			jsonObject.put("return-policy", getReturnFieldnames());
		} else {
			jsonObject.put("return-policy", getReturnPolicy());
		}
		if(getServicePolicy().equals(listed)) {
			jsonObject.put("service-policy", getPersistentServices());
		} else {
			jsonObject.put("service-policy", getServicePolicy());
		}
		jsonObject.put("block-till-finished", isBlockTillFinished());
		return jsonObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJson(Map<String, Object> data) {
		Object returnPolicy = Objects.requireNonNull(data.get("return-policy"));
		Object servicePolicy = Objects.requireNonNull(data.get("service-policy"));
		
		if(returnPolicy instanceof String) {
			setReturnPolicy((String) returnPolicy);
		} else if(returnPolicy instanceof List) {
			setReturnFieldnames((List<String>) returnPolicy);
		}
		else {
			log.error("return policy type mismatch: "  + returnPolicy.toString());
		}
		
		if(servicePolicy instanceof String) {
			setServicePolicy((String) servicePolicy);
		} else if(servicePolicy instanceof List) {
			setPersistentServices((List<String>) servicePolicy);
		}
		else {
			log.error("service policy type mismatch: "  + servicePolicy.toString());
		}
		Boolean blockTillFinished = (Boolean) data.get("block-till-finished");
		if(blockTillFinished!=null) {
			this.setBlockTillFinished(blockTillFinished);
		}
	}
}
