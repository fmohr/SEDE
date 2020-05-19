package de.upb.sede.composition.graphs;

import de.upb.sede.exec.ExecutorHandle;
import de.upb.sede.exec.IExecutorHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ExecPlan_1 {
	private final CompositionGraph graph;
	private final List<IExecutorHandle> executors;

	ExecPlan_1(List<IExecutorHandle> executors) {
		this.graph = new CompositionGraph();
		this.executors = new ArrayList<>(executors);
		removeCandidates(candidates-> candidates == null); // remove all nulls.
		checkCandidateListNotEmpty();
	}

	ExecPlan_1(IExecutorHandle determinedExecutor) {
		this.graph = new CompositionGraph();
		this.executors = new ArrayList<>();
		executors.add(Objects.requireNonNull(determinedExecutor));
	}


	public CompositionGraph getGraph() {
		return graph;
	}

	public IExecutorHandle getTarget() {
		if(targetDetermined()) {
			return executors.get(0);
		}
		throw new RuntimeException("CODE BUG: Target isn't determined yet. Consider every executor as a possiblity.");
	}

	public boolean targetDetermined() {
		return executors.size() == 1;
	}

	public List<IExecutorHandle> candidates(){
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

	public void removeCandidates(Predicate<IExecutorHandle> filter) {
		this.executors.removeIf(filter);
		checkCandidateListNotEmpty();

	}

	private void checkCandidateListNotEmpty() {
		if(executors.isEmpty()) {
			throw new RuntimeException("CODE BUG: The exec plan has no more candidates.");
		}
	}
}
