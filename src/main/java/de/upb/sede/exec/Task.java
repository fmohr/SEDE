package de.upb.sede.exec;

import java.util.Map;
import java.util.Objects;

public final class Task {
	private final Execution execution;
	private final String taskName;
	private final Map<String, Object> attributes;

	public Task(Execution execution, String taskName, Map<String, Object> parameters) {
		this.execution = Objects.requireNonNull(execution);
		this.taskName = Objects.requireNonNull(taskName);
		this.attributes = Objects.requireNonNull(parameters);
	}

	public Execution getExecution() {
		return execution;
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

}
