package de.upb.sede.gateway;

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
		throw new RuntimeException("No executor found for host. First query hasExecutor before retrieving it.");
	}

	public synchronized void addExecutor(ExecutorHandle eh) {
		this.executors.add(Objects.requireNonNull(eh));
	}

	public List<ExecutorHandle> getExecutors() {
		return executors;
	}

	public synchronized void removeExecutor(String executorId) {
		for(Iterator<ExecutorHandle> iterator = executors.iterator(); iterator.hasNext();) {
			ExecutorHandle handle = iterator.next();
			if(handle.getExecutorId().equals(executorId)) {
				iterator.remove();
			}
		}
	}
}
