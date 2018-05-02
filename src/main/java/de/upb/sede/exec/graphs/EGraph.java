package de.upb.sede.exec.graphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionGraph;
import de.upb.sede.exec.Task;

public class EGraph implements ExecutionGraph {

	private Set<Task> tasks = new HashSet<>();
	private Map<Task, List<Task>> tasksDependingOnThis = new HashMap<>();
	private Execution execution;
	
	public EGraph(Execution execution) {
		Objects.requireNonNull(execution);
		this.execution = execution;
	}
	
	public void addTask(Task task) {

	}

	public void connectTasks(Task taskA, Task taskB) {

	}

	@Override
	public Execution getExecution() {
		return execution;
	}

}
