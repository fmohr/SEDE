package de.upb.sede.interfaces;

import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Deprecated
public interface ICoreClient {

	public String run(RunRequest runRequest, Consumer<Result> resultConsumer);

	public Map<String, Result> blockingRun(RunRequest runRequest);

	public void join(String requestId, boolean interruptExecution);

	public void join(String requestId, boolean interruptExecution, long timeout, TimeUnit timeUnit);

	public IExecutor getClientExecutor();

	public void interrupt(String requestId);

}
