package de.upb.sede.core;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;

import de.upb.sede.util.JsonSerializable;

public class ServiceInstanceHandle implements Serializable, JsonSerializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	private Optional<String> executorId;
	private Optional<String> classpath;
	private Optional<String> id;

	/**
	 * Standard constructor
	 */
	public ServiceInstanceHandle(final String executorId, final String classpath, final String id) {
		super();
		this.executorId = Optional.of(executorId);
		this.classpath = Optional.of(classpath);
		this.id = Optional.of(id);
	}

	/**
	 * Empty constructor to create service handle with default values.
	 */
	public ServiceInstanceHandle() {
		this.executorId = Optional.empty();
		this.classpath = Optional.empty();
		this.id = Optional.empty();
	}

	/**
	 * Returns the id of the service. Throws Runtime-Exception if wasSerialized()
	 * returns false.
	 */
	public String getId() {
		return this.id.get();
	}

	/**
	 *
	 * @return Host of this service.
	 */
	public String getExecutorId() {
		return this.executorId.get();
	}

	public String getClasspath() {
		return this.classpath.get();
	}

	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("executorId", getExecutorId());
		jsonObject.put("classpath", getClasspath());
		jsonObject.put("id", getId());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		this.executorId = Optional.of((String) data.get("executorId"));
		this.id = Optional.of((String) data.get("id"));
		this.classpath = Optional.of((String) data.get("classpath"));
	}

	public Optional<Object> getServiceInstance() {
		return Optional.empty();
	}

	public String toString() {
		return "cp: " + getClasspath() + " " + getId() + "/" + getExecutorId();
	}
}
