package de.upb.sede.exec;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ServiceProcessor {
	RequestPool requestPool = new RequestPool();
	ServiceManager serviceManager;
	ResourceAllocator resourceAllocator;
	ExecutorService threadPool;
	CompositionCall compositionCall;

	public ServiceProcessor(String executorConfigurationPath) throws Exception {
		ExecutorConfiguration executorConfiguration = ExecutorConfiguration.parse(executorConfigurationPath);  
		serviceManager = new ServiceManager(new File(executorConfiguration.getServiceStoreLocation()));
		threadPool = Executors.newFixedThreadPool(executorConfiguration.getThreadNumber());
		resourceAllocator = new ResourceAllocator(executorConfiguration.getAvailableResources());
	}

	public void setObjects(List<?> objectList) {
		if (isNewCompositionCall()) {
			compositionCall = new CompositionCall();
		}
		compositionCall.setObjectList(objectList);
	}

	public void setExecutionGraph(ExecutionGraph executionGraph) throws Exception {
		if (isNewCompositionCall()) {
			compositionCall = new CompositionCall();
		}
		compositionCall.setExecutionGraph(executionGraph);
	}

	private boolean isNewCompositionCall() {
		return compositionCall == null || compositionCall == CompositionCall.UNDEFINED_COMPOSITION_CALL;
	}
	
	public void process() {
		for(Node node :compositionCall.getExecutionGraph()) {
			ServiceThread serviceThread = new ServiceThread(node, resourceAllocator, serviceManager);
			ReentrantLock lock = new ReentrantLock();
			Condition threadExitCondition = lock.newCondition();
			serviceThread.setExitCondition(lock, threadExitCondition);
			serviceThread.start(threadPool);
		}			
	}
}
