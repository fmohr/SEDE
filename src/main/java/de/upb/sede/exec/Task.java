package de.upb.sede.exec;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Task {
	private Execution execution;
	private final String taskName;
	private Map<String, Object> parameters = Collections.emptyMap();

	Task(Execution execution, String taskName) {
		Objects.requireNonNull(execution);
		Objects.requireNonNull(taskName);
		this.execution = execution;
		this.taskName = taskName;
	}

	public void withParameters(Map<String, Object> parameters) {
		Objects.requireNonNull(parameters);
		if (parameters.size() > 0) {
			this.parameters = parameters;
		}
	}

	public Execution getExecution() {
		return execution;
	}

	public String getTaskName() {
		return taskName;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

}
