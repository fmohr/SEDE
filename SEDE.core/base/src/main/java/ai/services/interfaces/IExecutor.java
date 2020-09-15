package ai.services.interfaces;

import ai.services.requests.DataPutRequest;
import ai.services.requests.ExecRequest;
import ai.services.requests.ExecutorRegistration;

import java.util.Map;

public interface IExecutor {
	public void put(DataPutRequest dataPutRequest);

	public IExecution exec(ExecRequest execRequest);

	public void interrupt(String executionId);

	public void shutdown();

	public Map<String, Object> contactInfo();

	public ExecutorRegistration registration();
}
