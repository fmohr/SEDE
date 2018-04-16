package de.upb.sede.exec.graphs;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * Use the other task instead of this later on.
 * @author aminfaez
 *
 */
public final class Task {
	private final String requestID;
	private final String taskName;
	private final Map<String,Object> parameters;

	public Task(String requestID, String taskName, Map<String, Object> parameters) {
		Objects.requireNonNull(requestID);
		Objects.requireNonNull(taskName);
		this.requestID = requestID;
		this.taskName = taskName;
		this.parameters = parameters;
	}

	public String getRequestID() {
		return requestID;
	}

	public String getTaskName() {
		return taskName;
	}

	public Map<String,Object> getParameters() {
		return parameters;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public final boolean equals(Object otherObject) {
		return super.equals(otherObject);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public final int hashCode() {
		return super.hashCode();
	}

}
