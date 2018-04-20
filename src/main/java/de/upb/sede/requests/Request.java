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
	protected Optional<String> clientHost;

	public Request() {
		this.requestId = Optional.empty();
		this.clientHost = Optional.empty();
	}

	public Request(String requestId, String clientHost) {
		this.requestId = Optional.of(requestId);
		this.clientHost = Optional.of(clientHost);
	}

	public boolean hasRequestId() {
		return this.requestId.isPresent();
	}

	public boolean hasclientHost() {
		return this.clientHost.isPresent();
	}

	public String getRequestID() {
		assert hasRequestId();
		return requestId.get();
	}

	public String getClientHost() {
		assert hasclientHost();
		return clientHost.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("requestId", getRequestID());
		jsonObject.put("clientHost", getClientHost());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		requestId = Optional.of((String) data.get("requestId"));
		clientHost = Optional.of((String) data.get("clientHost"));
	}

}