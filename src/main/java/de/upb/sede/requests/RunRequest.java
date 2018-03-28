package de.upb.sede.requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RunRequest extends Request {
	private final String composition;
	private final String policy;

	private final static Map<String, Object> UNDEFINED_VARIABLES = new HashMap<>();
	private final Map<String, Object> variables;

	public RunRequest() {
		this.composition = UNDEFINED_COMPOSITION;
		this.policy = UNDEFINED_POLICY;
		this.variables = UNDEFINED_VARIABLES;

	}

	private RunRequest(String requestId, String clientHost, String composition, String policy,
			Map<String, Object> variables) {
		super(requestId, clientHost);
		this.composition = composition;
		this.policy = policy;
		this.variables = variables;
	}

	@Override
	public RunRequest withRequestId(String requestId) {
		return new RunRequest(Objects.requireNonNull(requestId), clientHost, composition, policy, variables);
	}

	@Override
	public RunRequest withClientHost(String clientHost) {
		return new RunRequest(requestId, Objects.requireNonNull(clientHost), composition, policy, variables);
	}

	public RunRequest withComposition(String composition) {
		return new RunRequest(requestId, clientHost, Objects.requireNonNull(composition), policy, variables);
	}

	public RunRequest withPolicy(String policy) {
		return new RunRequest(requestId, clientHost, composition, Objects.requireNonNull(policy), variables);
	}

	public RunRequest withVariables(Map<String, Object> variables) {
		return new RunRequest(requestId, clientHost, composition, policy, Objects.requireNonNull(variables));
	}

	public boolean hasComposition() {
		return this.composition != UNDEFINED_COMPOSITION;
	}

	public boolean hasPolicy() {
		return this.policy != UNDEFINED_POLICY;
	}

	public boolean hasVariables() {
		return this.variables != UNDEFINED_VARIABLES;
	}

	public String getComposition() {
		assert hasComposition();
		return composition;
	}

	public String getPolicy() {
		assert hasPolicy();
		return policy;
	}

	public Map<String, Object> getVariables() {
		assert hasVariables();
		return variables;
	}
}
