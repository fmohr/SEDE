package de.upb.sede.exec;

public interface ExecutionGraph extends Iterable<Task> {
	public Task getNextTask();
}
