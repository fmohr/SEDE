package de.upb.sede.exec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Task implements TaskObserver {
	private final ExecutionGraph graph;
	private final String taskName;
	private final Map<String, Object> attributes;

	private List<TaskObserver> observers = new ArrayList<>();

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

	/**
	 * This method is invoked when any of the observed tasks change their state.
	 * The Task itself is given as an argument to the invokation.
	 * <p>
	 * When implementing this function be aware of racing conditions.
	 * There can be multiple threads in this method at once as the notifyObservers functions in Task is usually called by workers.
	 *
	 * @param updatedTask the task whose state is changed.
	 */
	@Override
	public void taskUpdated(Task updatedTask) {
		// TODO update the resolved flag.
	}

	/**
	 * Adds an observer to this task.
	 *
	 * @param observer observer that will be added to the observers list.
	 */
	public void addObserver(TaskObserver observer) {
		synchronized (this.observers) {
			observers.add(observer);
		}
	}

	/**
	 * All observers who were added by addObserver are notified by calling taskUpdated using this instance as the argument.
	 * The order in which observers are added is the same as they are n
	 */
	public void notifyObserver() {
		synchronized (this.observers) {
			for (TaskObserver obs : this.observers) {
				obs.taskUpdated(this);
			}
		}
	}
}
