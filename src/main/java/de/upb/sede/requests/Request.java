package de.upb.sede.requests;

import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;

import de.upb.sede.util.JsonSerializable;

/**
 * All instances of request should be immutable.
 */
public abstract class Request implements JsonSerializable {

	protected Optional<String> requestId;

	public Request() {
		this.requestId = Optional.empty();
	}

	public Request(String requestId) {
		this.requestId = Optional.of(requestId);
	}

	public boolean hasRequestId() {
		return this.requestId.isPresent();
	}


	public String getRequestID() {
		assert hasRequestId();
		return requestId.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("requestId", getRequestID());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		requestId = Optional.of((String) data.get("requestId"));
	}

}