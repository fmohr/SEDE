package de.upb.sede.composition.gc;

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
	private Optional<String> host;
	private Optional<String> classpath;
	private Optional<String> id;

	/**
	 * Standard constructor
	 */
	public ServiceInstanceHandle(final String host, final String classpath, final String id) {
		super();
		this.host = Optional.of(host);
		this.classpath = Optional.of(classpath);
		this.id = Optional.of(id);
	}

	/**
	 * Empty constructor to create service handle with default values.
	 */
	public ServiceInstanceHandle() {
		this.host = Optional.empty();
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
	public String getHost() {
		return this.host.get();
	}

	public String getClasspath() {
		return this.classpath.get();
	}

	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("host", getHost());
		jsonObject.put("classpath", getClasspath());
		jsonObject.put("id", getId());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		this.host = Optional.ofNullable((String) data.get("host"));
		this.id = Optional.ofNullable((String) data.get("id"));
		this.classpath = Optional.ofNullable((String) data.get("classpath"));
	}
}
