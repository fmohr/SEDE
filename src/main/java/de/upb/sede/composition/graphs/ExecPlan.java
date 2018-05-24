package de.upb.sede.composition.graphs;

import de.upb.sede.gateway.ExecutorHandle;

public class ExecPlan {
	private final CompositionGraph graph;
	private final ExecutorHandle executor;

	ExecPlan(ExecutorHandle executor) {
		this.graph = new CompositionGraph();
		this.executor = executor;
	}

	public CompositionGraph getGraph() {
		return graph;
	}

	public ExecutorHandle getExecutor() {
		return executor;
	}

}
