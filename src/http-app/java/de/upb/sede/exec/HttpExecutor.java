package de.upb.sede.exec;

import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;

public class HttpExecutor extends Executor{

	public HttpExecutor(ExecutorConfiguration execConfig) throws Exception {
		super(execConfig, new HttpExecutionPool(execConfig));
	}




}
class HttpExecutionPool extends de.upb.sede.exec.ExecutionPool {

	public HttpExecutionPool(ExecutorConfiguration config) {
		super(config);
	}

	@Override
	protected Execution executionSupplier(String execId) {
		return new Execution(execId) {
			@Override
			public BasicClientRequest createClientRequest(Object requestInfo) {
				return new HTTPClientRequest(requestInfo.toString());
			}

			@Override
			public ServiceInstanceHandle createServiceInstanceHandle(Object serviceInstance) {
				ServiceInstance handle = new ServiceInstance();
			}
		};
	}
}
