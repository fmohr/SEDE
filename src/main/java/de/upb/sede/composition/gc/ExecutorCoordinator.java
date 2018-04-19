package de.upb.sede.composition.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Offers synchronized access to executor handles.
 * @author aminfaez
 *
 */
public class ExecutorCoordinator {
	private List<ExecutorHandle> executors;
	
	public synchronized List<ExecutorHandle> executorsSupportingServiceClass(String ServiceClass){
		List<ExecutorHandle> capableExecutors = new ArrayList<>();
		for(ExecutorHandle executor : executors) {
			if(executor.getExecutionerCapabilities().supportsServiceClass(ServiceClass)) {
				capableExecutors.add(executor);
			}
		}
		return capableExecutors;
	}
	
	public synchronized boolean hasExecutor(String execHostAddress) {
		return executors.stream().anyMatch(handle -> handle.equalsHostAddress(execHostAddress));
	}

	public synchronized ExecutorHandle getExecutorFor(String host) {
		for(ExecutorHandle executor : executors) {
			if(executor.getHostAddress().equals(host)) {
				return executor;
			}
		}
		throw new RuntimeException("No executor found for host. First query hasExecutor before retrieving it.");
	}
	public synchronized void addExecutor(ExecutorHandle eh) {
		this.executors.add(Objects.requireNonNull(eh));
	}
}
