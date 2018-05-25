package de.upb.sede.interfaces;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.requests.Request;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;

import java.util.Map;
import java.util.function.Consumer;

public interface ICoreClient {

	public String run(RunRequest runRequest, Consumer<Result> resultConsumer);

	public Map<String, SEDEObject> blockingRun(RunRequest runRequest);

	public void join(String requestId, boolean interruptExecution);

	public IExecutor getClientExecutor();

	public void interrupt(String requestId);

}