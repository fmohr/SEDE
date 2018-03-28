package de.upb.sede.requests;

import java.util.Map;
import java.util.Objects;

public class DataPutRequest extends Request {

	private final Map<String, Object> variables;

	public DataPutRequest() {
		this.variables = UNDEFINED_VARIABLES;
	}

	private DataPutRequest(String requestId, String clientHost, Map<String, Object> variables) {
		super(requestId, clientHost);
		this.variables = variables;
	}

	@Override
	public DataPutRequest withRequestId(String requestId) {
		return new DataPutRequest(Objects.requireNonNull(requestId), clientHost, variables);
	}

	@Override
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
