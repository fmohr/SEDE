package de.upb.sede.exec;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Task {
	private final String requestID;
	private final String taskName;
	private Map<String,Object> parameters = Collections.emptyMap();

	Task(String requestID, String taskName) {
		Objects.requireNonNull(requestID);
		Objects.requireNonNull(taskName);
		this.requestID = requestID;
		this.taskName = taskName;
	}

	public void withParameters(Map<String,Object> parameters) {
		Objects.requireNonNull(parameters);
		if (parameters.size() > 0) {
			this.parameters = parameters;
		}
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

}
