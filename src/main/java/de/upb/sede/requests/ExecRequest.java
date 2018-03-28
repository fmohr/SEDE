package de.upb.sede.requests;

import java.util.Objects;

public class ExecRequest {

	private final static String UNDEFINED_ExecRequestID = "NO_RID";
	private final String ExecRequestId;

	private final static String UNDEFINED_CLIENTHOST = "NO_CLIENTHOST";
	private final String clientHost;

	private final static String UNDEFINED_COMPOSITIONGRAPH = "NO_COMPOSITIONGRAPH";
	private final String compositionGraph;

	public ExecRequest() {
		this.ExecRequestId = UNDEFINED_ExecRequestID;
		this.clientHost = UNDEFINED_CLIENTHOST;
		this.compositionGraph = UNDEFINED_COMPOSITIONGRAPH;

	}

	private ExecRequest(String ExecRequestId, String clientHost, String compositionGraph) {
		this.ExecRequestId = ExecRequestId;
		this.clientHost = clientHost;
		this.compositionGraph = compositionGraph;
	}

	/*
	 * With methods
	 */
	public ExecRequest withExecRequestId(String ExecRequestId) {
		return new ExecRequest(Objects.requireNonNull(ExecRequestId), clientHost, compositionGraph);
	}

	public ExecRequest withClientHost(String clientHost) {
		return new ExecRequest(ExecRequestId, Objects.requireNonNull(clientHost), compositionGraph);
	}

	public ExecRequest withCompositionGraph(String compositionGraph) {
		return new ExecRequest(ExecRequestId, clientHost, Objects.requireNonNull(compositionGraph));
	}

	/*
	 * has methods
	 */

	public boolean hasExecRequestId() {
		return this.ExecRequestId != UNDEFINED_ExecRequestID;
	}

	public boolean hasclientHost() {
		return this.clientHost != UNDEFINED_CLIENTHOST;
	}

	public boolean hasCompositionGraph() {
		return this.compositionGraph != UNDEFINED_COMPOSITIONGRAPH;
	}

	/*
	 * get methods
	 */

	public String getExecRequestID() {
		assert hasExecRequestId();
		return ExecRequestId;
	}

	public String getClientHost() {
		assert hasclientHost();
		return clientHost;
	}

	public String getCompositionGraph() {
		assert hasCompositionGraph();
		return compositionGraph;
	}
}
