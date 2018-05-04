package de.upb.sede.exec;

import de.upb.sede.util.DefaultMap;

import java.util.Collection;

public abstract class Executor {
	private ExecutionPool requestPool;
	private ResourceAllocator resourceAllocator;
	private Collection<Worker> workerpool;


	public Executor(String executorConfigurationPath) throws Exception {
		ExecutorConfiguration executorConfiguration = ExecutorConfiguration.parse(executorConfigurationPath);
		resourceAllocator = new ResourceAllocator(executorConfiguration.getAvailableResources());
	}




	public abstract void start();
}
