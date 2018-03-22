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

	private final static Map<String, Object> UNDEFINED_VARIABLES = new HashMap<>();
	private final Map<String, Object> variables;

	public Request() {
		this.requestId = UNDEFINED_REQUESTID;
		this.clientHost = UNDEFINED_CLIENTHOST;
		this.composition = UNDEFINED_COMPOSITION;
		this.compositionGraph = UNDEFINED_COMPOSITIONGRAPH;
		this.variables = UNDEFINED_VARIABLES;
	}

	private Request(String requestId, String clientHost, String composition, String compositionGraph,
			Map<String, Object> variables) {
		this.requestId = requestId;
		this.clientHost = clientHost;
		this.compositionGraph = compositionGraph;
		this.composition = composition;
		this.variables = variables;
	}

	/*
	 * With methods
	 */
	public Request withRequestId(String requestId) {
		return new Request(Objects.requireNonNull(requestId), clientHost, composition, compositionGraph, variables);
	}

	public Request withClientHost(String clientHost) {
		return new Request(requestId, Objects.requireNonNull(clientHost), composition, compositionGraph, variables);
	}

	public Request withComposition(String composition) {
		return new Request(requestId, clientHost, Objects.requireNonNull(composition), compositionGraph, variables);
	}

	public Request withCompositionGraph(String compositionGraph) {
		return new Request(requestId, clientHost, composition, Objects.requireNonNull(compositionGraph), variables);
	}

	public Request withVariables(Map<String, Object> variables) {
		return new Request(requestId, clientHost, composition, compositionGraph, Objects.requireNonNull(variables));
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

	public Map<String, Object> getVariables() {
		assert hasVariables();
		return variables;
	}
}