package de.upb.sede.interfaces;

import de.upb.sede.exec.Execution;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.Request;
import org.json.simple.JSONObject;

import java.util.Map;

public interface IExecutor {
	public void put(DataPutRequest dataPutRequest);

	public Execution exec(ExecRequest execRequest);

	public void loadServices(Object Services);

	public void interrupt(String executionId);

	public Map<String, String> contactInfo();

	public ExecutorRegistration registration();
}