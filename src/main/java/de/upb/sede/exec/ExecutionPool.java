package de.upb.sede.exec;

import de.upb.sede.util.Observer;

import java.util.HashMap;
import java.util.Map;

public abstract class ExecutionPool {
	private final Map<String, Execution> execMap = new HashMap<>();


	private final Observer<Execution> executionObserver = Observer.lambda(	exec -> exec.executionDone(),  // when an execution is done, ..
																			exec -> removeExecution(exec.getExecutionId())); // remove it.

	public synchronized Execution getOrCreateExecution(String execId) {
		Execution exec = execMap.get(execId);
		if(exec == null) {
			/*
			 * The given id doesn't have any execution assigned to it.
			 * Create a new Execution for the given request id:
			 */
			exec = executionSupplier(execId);
			execMap.put(execId, exec);
			exec.get
		}
		return exec;
	}

	public synchronized boolean hasExecution(String execId) {
		return execMap.containsKey(execId);
	}

	public synchronized void removeExecution(String execId) {
		if(hasExecution(execId)){
			execMap.remove(execId);
		}
	}

	/**
	 * Supplier of execution instances. <p>
	 * Given a execution-id this method creates a new Execution object without altering the state of the execution pool.
	 *
	 * @param execId request id of the new execution object.
	 *
	 * @return A fresh new execution object.
	 */
	protected abstract Execution executionSupplier(String execId);

	/**
	 * Scheduler of tasks.
	 * This method decides which task is to be processed next.
	 *
	 * @return The task that is to be processed next.
	 */
	public abstract Task getNextTask();
}
