package de.upb.sede.exec;

import java.util.Collection;

public abstract class Executor {
	ExecutionPool requestPool;
	ResourceAllocator resourceAllocator;
	Collection<Worker> workerpool;

	public Executor(String executorConfigurationPath) throws Exception {
		ExecutorConfiguration executorConfiguration = ExecutorConfiguration.parse(executorConfigurationPath);
		resourceAllocator = new ResourceAllocator(executorConfiguration.getAvailableResources());
	}
	
	public abstract void start();
}
