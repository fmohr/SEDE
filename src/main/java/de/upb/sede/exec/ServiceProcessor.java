package de.upb.sede.exec;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceProcessor {
	RequestPool requestPool;
	ResourceAllocator resourceAllocator;
	ExecutorService threadPool;
	CompositionCall compositionCall;

	public ServiceProcessor(String executorConfigurationPath) throws Exception {
		ExecutorConfiguration executorConfiguration = ExecutorConfiguration.parse(executorConfigurationPath); 
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
			//TODO Process requests from the request pool.	
	}
}
