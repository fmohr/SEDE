package de.upb.sede.composition.graphs;

import de.upb.sede.gateway.ExecutorHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ExecPlan {
	private final CompositionGraph graph;
	private final List<ExecutorHandle> executors;

	ExecPlan(List<ExecutorHandle> executors) {
		this.graph = new CompositionGraph();
		this.executors = new ArrayList<>(executors);
		removeCandidates(candidates-> candidates == null); // remove all nulls.
		checkCandidateListNotEmpty();
	}

	ExecPlan(ExecutorHandle determinedExecutor) {
		this.graph = new CompositionGraph();
		this.executors = new ArrayList<>();
		executors.add(Objects.requireNonNull(determinedExecutor));
	}



	public CompositionGraph getGraph() {
		return graph;
	}

	public ExecutorHandle getTarget() {
		if(targetDetermined()) {
			return executors.get(0);
		}
		throw new RuntimeException("CODE BUG: Target isn't determined yet. Consider every executor as a possiblity.");
	}

	public boolean targetDetermined() {
		return executors.size() == 1;
	}

	public List<ExecutorHandle> candidates(){
		return executors;
	}


	public void removeCandidate(ExecutorHandle executorHandle) {
		if(executors.contains(executorHandle)) {
			executors.remove(executorHandle);
		} else {
			throw new RuntimeException("CODE BUG: Cannot remove a executor which isn't a candidate.");
		}
		checkCandidateListNotEmpty();
	}

	public void removeCandidates(Predicate<ExecutorHandle> filter) {
		this.executors.removeIf(filter);
		checkCandidateListNotEmpty();

	}

	private void checkCandidateListNotEmpty() {
		if(executors.isEmpty()) {
			throw new RuntimeException("CODE BUG: The exec plan has no more candidates.");
		}
	}
}
