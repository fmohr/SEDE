package de.upb.sede.requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataPutRequest extends Request{
	
	private final Map<String, Object> variables;

	public DataPutRequest() {
		this.variables = UNDEFINED_VARIABLES;

	}

	private DataPutRequest(String requestId, String clientHost, Map<String, Object> variables) {
		this.requestId = requestId;
		this.clientHost = clientHost;
		this.variables = variables;
	}

	public DataPutRequest withRequestId(String requestId) {
		return new DataPutRequest(Objects.requireNonNull(requestId), clientHost, variables);
	}

	public DataPutRequest withClientHost(String clientHost) {
		return new DataPutRequest(requestId, Objects.requireNonNull(clientHost), variables);
	}

	public DataPutRequest withVariables(Map<String, Object> variables) {
		return new DataPutRequest(requestId, clientHost, Objects.requireNonNull(variables));
	}

	public boolean hasVariables() {
		return this.variables != UNDEFINED_VARIABLES;
	}

	public Map<String, Object> getVariables() {
		assert hasVariables();
		return variables;
	}
}
