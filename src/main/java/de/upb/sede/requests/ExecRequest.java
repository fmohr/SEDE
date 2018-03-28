package de.upb.sede.requests;

import java.util.Objects;

public class ExecRequest extends Request {
	private final String compositionGraph;

	public ExecRequest() {
		this.compositionGraph = UNDEFINED_COMPOSITIONGRAPH;
	}

	private ExecRequest(String requestId, String clientHost, String compositionGraph) {
		super(requestId, clientHost);
		this.compositionGraph = compositionGraph;
	}

	@Override
	public ExecRequest withRequestId(String requestId) {
		return new ExecRequest(Objects.requireNonNull(requestId), clientHost, compositionGraph);
	}

	@Override
	public ExecRequest withClientHost(String clientHost) {
		return new ExecRequest(requestId, Objects.requireNonNull(clientHost), compositionGraph);
	}

	public ExecRequest withCompositionGraph(String compositionGraph) {
		return new ExecRequest(requestId, clientHost, Objects.requireNonNull(compositionGraph));
	}

	public boolean hasCompositionGraph() {
		return this.compositionGraph != UNDEFINED_COMPOSITIONGRAPH;
	}

	public String getCompositionGraph() {
		assert hasCompositionGraph();
		return compositionGraph;
	}
}
