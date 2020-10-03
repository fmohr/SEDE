package ai.services.exceptions;

public class DependecyTaskFailed extends RuntimeException {
	public DependecyTaskFailed(String taskDebugString) {
		super(String.format("Task '%s' failed because one of its dependencies failed before.", taskDebugString));
	}
}
