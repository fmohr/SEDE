package de.upb.sede.exec;

import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;

public class HttpExecutionPool extends ExecutionPool {
	@Override
	protected Execution executionSupplier(String execId) {
		return new Execution(execId) {
			@Override
			public BasicClientRequest createClientRequest(Object requestInfo) {
				return new HTTPClientRequest(requestInfo.toString());
			}

			@Override
			public ServiceInstanceHandle createServiceInstanceHandle(Object serviceInstance) {
				return null;
			}
		};
	}
}
