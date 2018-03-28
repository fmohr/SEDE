package de.upb.sede.requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RunRequest {

	private final static String UNDEFINED_RunRequestID = "NO_RID";
	private final String RunRequestId;

	private final static String UNDEFINED_CLIENTHOST = "NO_CLIENTHOST";
	private final String clientHost;

	private final static String UNDEFINED_COMPOSITION = "NO_COMPOSITION";
	private final String composition;

	private final static String UNDEFINED_POLICY = "NO_POLICY";
	private final String policy;

	private final static Map<String, Object> UNDEFINED_VARIABLES = new HashMap<>();
	private final Map<String, Object> variables;

	public RunRequest() {
		this.RunRequestId = UNDEFINED_RunRequestID;
		this.clientHost = UNDEFINED_CLIENTHOST;
		this.composition = UNDEFINED_COMPOSITION;
		this.policy = UNDEFINED_POLICY;
		this.variables = UNDEFINED_VARIABLES;

	}

	private RunRequest(String RunRequestId, String clientHost, String composition, String policy,
			Map<String, Object> variables) {
		this.RunRequestId = RunRequestId;
		this.clientHost = clientHost;
		this.composition = composition;
		this.policy = policy;
		this.variables = variables;
	}

	/*
	 * With methods
	 */
	public RunRequest withRunRequestId(String RunRequestId) {
		return new RunRequest(Objects.requireNonNull(RunRequestId), clientHost, composition, policy,
				variables);
	}

	public RunRequest withClientHost(String clientHost) {
		return new RunRequest(RunRequestId, Objects.requireNonNull(clientHost), composition, policy,
				variables);
	}

	public RunRequest withComposition(String composition) {
		return new RunRequest(RunRequestId, clientHost, Objects.requireNonNull(composition), policy,
				variables);
	}

	public RunRequest withPolicy(String policy) {
		return new RunRequest(RunRequestId, clientHost, composition, Objects.requireNonNull(policy),
				variables);
	}

	public RunRequest withVariables(Map<String, Object> variables) {
		return new RunRequest(RunRequestId, clientHost, composition, policy,
				Objects.requireNonNull(variables));
	}

	/*
	 * has methods
	 */

	public boolean hasRunRequestId() {
		return this.RunRequestId != UNDEFINED_RunRequestID;
	}

	public boolean hasclientHost() {
		return this.clientHost != UNDEFINED_CLIENTHOST;
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

	/*
	 * get methods
	 */

	public String getRunRequestID() {
		assert hasRunRequestId();
		return RunRequestId;
	}

	public String getClientHost() {
		assert hasclientHost();
		return clientHost;
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
