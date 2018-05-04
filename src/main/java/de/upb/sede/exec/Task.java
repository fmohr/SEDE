package de.upb.sede.exec;

import de.upb.sede.util.Observable;
import de.upb.sede.util.Observer;

import java.util.*;

public final class Task implements Observer<Task>{

	private final ExecutionGraph graph;

	private final String taskName;

	private final Map<String, Object> attributes;

	private final Set<Task> dependencies = new HashSet<>();

	/*
	 * Flags which define the state of the task.
	 */
	private boolean resolved = false,
					started = false,
					failed = false,
					succeeded = false;


	private Observable<Task> taskState = Observable.ofInstance(this);



	public Task(ExecutionGraph graph, String taskName, Map<String, Object> parameters) {
		this.graph = Objects.requireNonNull(graph);
		this.taskName = Objects.requireNonNull(taskName);
		this.attributes = Objects.requireNonNull(parameters);
	}

	public ExecutionGraph getExecutionGraph() {
		return graph;
	}

	public String getTaskName() {
		return taskName;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public final boolean equals(Object otherObject) {
		return super.equals(otherObject);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	public final int hashCode() {
		return super.hashCode();
	}

	public Observable<Task> getState(){
		return taskState;
	}

	public void addDependency(Task task) {
		this.dependencies.add(task);
		task.getState().observe(this);
	}

	/**
	 * @param task  task with updated state
	 * @return true if the task is done and the dependency contains this task.
	 */
	@Override
	public boolean notifyCondition(Task task) {
		return task.isDone() && this.dependencies.contains(task);
	}

	/**
	 * Notification is invoked when a dependency task is done (failed or succeeded).
	 * @param task dependency task.
	 */
	@Override
	public void notification(Task task) {
		if(this.hasFailed()){
			/* this task may have failed already if another dependency of this task has failed. */
			return;
		}
		this.dependencies.remove(task);
		if(task.hasFailed()){
			failed();
		}
		else if(dependencies.isEmpty()){
			setResolved();
		}
	}

	@Override
	public boolean synchronizedNotification() {
		return true;
	}

	public boolean isResolved(){
		return resolved;
	}

	public boolean hasStarted() {
		return started;
	}

	public boolean isRunning() {
		return hasStarted() && ! isDone();
	}

	public boolean isDone(){
		return hasFailed() || hasSucceeded();
	}

	public boolean hasFailed(){
		return failed;
	}

	public boolean hasSucceeded(){
		return succeeded;
	}

	public void setResolved(){
		resolved = true;
		taskState.update(this);
	}

	public void setStarted(){
		resolved = true;
		started = true;
		taskState.update(this);
	}

	public void succeeded(){
		resolved = true;
		started = true;
		succeeded = true;
		taskState.update(this);
	}

	public void failed(){
		resolved = true;
		started = true;
		failed = true;
		taskState.update(this);
	}


}
