package de.upb.sede.composition.gc;

import java.util.ArrayList;
import java.util.List;

public class ExecutorCoordinator {
	private List<ExecutorHandle> executors;
	
	public List<ExecutorHandle> executorsSupportingServiceClass(String ServiceClass){
		List<ExecutorHandle> capableExecutors = new ArrayList<>();
		for(ExecutorHandle executor : executors) {
			if(executor.getExecutionerCapabilities().supportsServiceClass(ServiceClass)) {
				capableExecutors.add(executor);
			}
		}
		return capableExecutors;
	}
	
	public boolean hasExecutor(String execHostAddress) {
		return executors.stream().anyMatch(handle -> handle.equalsHostAddress(execHostAddress));
	}

	public ExecutorHandle getExecutorFor(String host) {
		for(ExecutorHandle executor : executors) {
			if(executor.getHostAddress().equals(host)) {
				return executor;
			}
		}
		throw new RuntimeException("No executor found for host. First query hasExecutor before retrieving it.");
	}
	
}
