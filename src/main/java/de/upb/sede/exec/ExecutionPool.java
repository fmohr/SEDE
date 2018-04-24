package de.upb.sede.exec;

import java.util.Collection;

public abstract class ExecutionPool {
	Collection<Execution> executionPool;

	public abstract Task getNextTask();

	public abstract void enqueueRequest(Execution request);
}
