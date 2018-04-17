package de.upb.sede.composition.gc;

import java.util.List;
import java.util.Objects;

import de.upb.sede.exceptions.BadResolveRequest;

/**
 * TODO move this class to a better location later on.
 * 
 * @author aminfaez
 *
 */
public class ResolvePolicy {

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
		setReturnPolicy(all);
		setServicePolicy(all);
	}

	public String getReturnPolicy() {
		return returnPolicy;
	}

	public void setReturnPolicy(String returnPolicy) {
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

	public void setServicePolicy(String servicePolicy) {
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

	public void setReturnFieldnames(List<String> returnFieldnames) {
		this.returnFieldnames = returnFieldnames;
	}

	public List<String> getPersistentServices() {
		return persistentServices;
	}

	public void setPersistentServices(List<String> persistentServices) {
		this.persistentServices = persistentServices;
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

	public static ResolvePolicy fromJson(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
}
