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
	
}
