package de.upb.sede.gateway;

import de.upb.sede.composition.RoundRobinScheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Offers synchronized access to executor handles.
 * 
 * @author aminfaez
 *
 */
public class ExecutorCoordinator {

	/*
	 * The scheduler:
	 */
	private final RoundRobinScheduler scheduler = new RoundRobinScheduler();

	private final List<ExecutorHandle> executors = new ArrayList<>();

	public synchronized List<ExecutorHandle> executorsSupportingServiceClass(String ServiceClass) {
		List<ExecutorHandle> capableExecutors = new ArrayList<>();
		for (ExecutorHandle executor : executors) {
			if (executor.getExecutionerCapabilities().supportsServiceClass(ServiceClass)) {
				capableExecutors.add(executor);
			}
		}
		return capableExecutors;
	}

	public synchronized ExecutorHandle executorsWithServiceClass(String ServiceClass) {
		List<ExecutorHandle> executors = executorsSupportingServiceClass(ServiceClass);
		if(executors.isEmpty()) {
			throw new RuntimeException("No registered executor supports the given class: " + ServiceClass);
		}
		int randomIndex = (int) (executors.size() * Math.random());
		return executors.get(randomIndex);
	}

	public synchronized boolean hasExecutor(String id) {
		return executors.stream().anyMatch(h -> h.equalsId(id));
	}

	public synchronized ExecutorHandle getExecutorFor(String id) {
		for (ExecutorHandle executor : executors) {
			if (executor.getExecutorId().equals(id)) {
				return executor;
			}
		}
		throw new RuntimeException("No executor found for id=`" + id + "`. First query hasExecutor before retrieving it.");
	}

	synchronized void addExecutor(ExecutorHandle eh) {
		this.executors.add(Objects.requireNonNull(eh));
		scheduler.updateExecutors(executors);
	}

	List<ExecutorHandle> getExecutors() {
		return new ArrayList<>(executors);
	}

	synchronized void removeExecutor(String executorId) {
		for(Iterator<ExecutorHandle> iterator = executors.iterator(); iterator.hasNext();) {
			ExecutorHandle handle = iterator.next();
			if(handle.getExecutorId().equals(executorId)) {
				iterator.remove();
			}
		}
	}

	public ExecutorHandle scheduleNextAmong(List<ExecutorHandle> candidates) {
		String id = scheduler.scheduleNextAmong(candidates);
		for (ExecutorHandle executor : candidates) {
			if (executor.getExecutorId().equals(id)) {
				return executor;
			}
		}
		throw new RuntimeException("No executor found for for id=`\" + id + \"`.");
	}

	public RoundRobinScheduler getScheduler () {
		return scheduler;
	}
}
