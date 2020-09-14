package de.upb.sede.interfaces;

import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;

import java.util.Map;

public interface IExecutor {
	public void put(DataPutRequest dataPutRequest);

	public IExecution exec(ExecRequest execRequest);

	public void interrupt(String executionId);

	public void shutdown();

	public Map<String, Object> contactInfo();

	public ExecutorRegistration registration();
}
