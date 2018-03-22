package de.upb.sede.requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * All instances of request should be immutable.
 */
public class Request {

	private final static String UNDEFINED_REQUESTID = "NO_RID";
	private final String requestId;

	private final static String UNDEFINED_CLIENTHOST = "NO_CLIENTHOST";
	private final String clientHost;

	private final static String UNDEFINED_COMPOSITION = "NO_COMPOSITION";
	private final String composition;

	private final static String UNDEFINED_COMPOSITIONGRAPH = "NO_COMPOSITIONGRAPH";
	private final String compositionGraph;

	private final static String UNDEFINED_POLICY = "NO_POLICY";
	private final String policy;

	private final static Map<String, Object> UNDEFINED_VARIABLES = new HashMap<>();
	private final Map<String, Object> variables;

	public Request() {
		this.requestId = UNDEFINED_REQUESTID;
		this.clientHost = UNDEFINED_CLIENTHOST;
		this.composition = UNDEFINED_COMPOSITION;
		this.compositionGraph = UNDEFINED_COMPOSITIONGRAPH;
		this.policy = UNDEFINED_POLICY;
		this.variables = UNDEFINED_VARIABLES;

	}

	private Request(String requestId, String clientHost, String composition, String compositionGraph, String policy,
			Map<String, Object> variables) {
		this.requestId = requestId;
		this.clientHost = clientHost;
		this.composition = composition;
		this.compositionGraph = compositionGraph;
		this.policy = policy;
		this.variables = variables;
	}

	/*
	 * With methods
	 */
	public Request withRequestId(String requestId) {
		return new Request(Objects.requireNonNull(requestId), clientHost, composition, compositionGraph, policy,
				variables);
	}

	public Request withClientHost(String clientHost) {
		return new Request(requestId, Objects.requireNonNull(clientHost), composition, compositionGraph, policy,
				variables);
	}

	public Request withComposition(String composition) {
		return new Request(requestId, clientHost, Objects.requireNonNull(composition), compositionGraph, policy,
				variables);
	}

	public Request withCompositionGraph(String compositionGraph) {
		return new Request(requestId, clientHost, composition, Objects.requireNonNull(compositionGraph), policy,
				variables);
	}

	public Request withPolicy(String policy) {
		return new Request(requestId, clientHost, composition, compositionGraph, Objects.requireNonNull(policy),
				variables);
	}

	public Request withVariables(Map<String, Object> variables) {
		return new Request(requestId, clientHost, composition, compositionGraph, policy,
				Objects.requireNonNull(variables));
	}

	/*
	 * has methods
	 */

	public boolean hasRequestId() {
		return this.requestId != UNDEFINED_REQUESTID;
	}

	public boolean hasclientHost() {
		return this.clientHost != UNDEFINED_CLIENTHOST;
	}

	public boolean hasComposition() {
		return this.composition != UNDEFINED_COMPOSITION;
	}

	public boolean hasCompositionGraph() {
		return this.compositionGraph != UNDEFINED_COMPOSITIONGRAPH;
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

	public String getRequestID() {
		assert hasRequestId();
		return requestId;
	}

	public String getClientHost() {
		assert hasclientHost();
		return clientHost;
	}

	public String getComposition() {
		assert hasComposition();
		return composition;
	}

	public String getCompositionGraph() {
		assert hasCompositionGraph();
		return compositionGraph;
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