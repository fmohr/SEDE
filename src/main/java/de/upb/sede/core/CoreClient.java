package de.upb.sede.core;

import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Executor;
import de.upb.sede.interfaces.ICoreClient;
import de.upb.sede.interfaces.IExecutor;
import de.upb.sede.requests.*;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.function.Consumer;
import java.util.function.Function;

public class CoreClient implements ICoreClient{

	private static final Logger logger = LogManager.getLogger();

	private final Executor executor;
	private final Function<ResolveRequest, GatewayResolution> gatewayChannel;

	public CoreClient(Executor executor, Function<ResolveRequest, GatewayResolution> gatewayChannel)  {
		this.executor = executor;
		this.gatewayChannel = gatewayChannel;
	}

	@Override
	public Map<String, SEDEObject> blockingRun(RunRequest runRequest) {
		Map<String, SEDEObject> resultMap = new ConcurrentHashMap<>();
		Consumer<Result> resultConsumer = result -> resultMap.put(result.getFieldname(), result.getResultData());
		String execId = run(runRequest, resultConsumer);

		join(execId, true);
		return resultMap;

	}

	@Override
	public void join(String requestId, boolean interruptExecution) {
		Execution execution = executor.getExecution(requestId);
		if(execution == null) {
			return;
		}
		final Semaphore executionIsFinished = new Semaphore(0);
		final Object dummy = new Object();

		Observer<Execution> executionDoneObserver = Observer.lambda(Execution::hasExecutionFinished, exec -> {
			executionIsFinished.release();
		});

		execution.getState().observe(executionDoneObserver);
		try {
			executionIsFinished.acquire();
		} catch (InterruptedException e) {
			if (interruptExecution) {
				getClientExecutor().interrupt(requestId);
			}
		}
	}

	@Override
	public String run(RunRequest runRequest, Consumer<Result> resultConsumer) {
		if(resultConsumer == null) {
			resultConsumer = CoreClient::logResult;
		} else if(logger.isDebugEnabled()) {
			resultConsumer = resultConsumer.andThen(CoreClient::logResult);
		}
		/* choose a random request id */
		String requestId = UUID.randomUUID().toString();

		/* make a resolve request from the run request */
		ResolveRequest resolveRequest = runToResolve(runRequest, requestId);

		/* let a gateway resolve this request */
		GatewayResolution resolution = resolve(resolveRequest);

		/* Create a execution request from the resolution and run it on the client executor */
		ExecRequest execRequest = resolutionToExec(requestId, resolution);
		Execution execution = getClientExecutor().exec(execRequest);
		/* hand down the inputs for the execution to the executor */
		deliverInputs(requestId, runRequest.getInputs());

		List<String> resultFields = resolution.getReturnFields();
		logger.debug("waiting for {}", resultFields);
		ResultObserver resultObserver = new ResultObserver(requestId, resultFields, resultConsumer);
		execution.getEnvironment().getState().observe(resultObserver);

		logger.debug("Run-Request {} started.", requestId);
		return requestId;
	}

	public final ResolveRequest runToResolve(RunRequest runRequest, String resolveId) {
		String composition = runRequest.getComposition();
		ResolvePolicy policy = runRequest.getPolicy();

		ExecutorRegistration registration = getClientExecutor().registration();

		Map<String, SEDEObject> inputs = runRequest.getInputs();
		Map<String, String> inputTypes = new HashMap<>();
		Map<String, ServiceInstanceHandle> inputInstances = new HashMap<>();
		for (String fieldname : inputs.keySet()) {
			SEDEObject field = inputs.get(fieldname);
			inputTypes.put(fieldname, field.getType());
			if(field.isServiceInstanceHandle()) {
				inputInstances.put(fieldname, field.getServiceHandle());
			}
		}
		InputFields inputFields = new InputFields(inputTypes, inputInstances);

		ResolveRequest resolveRequest = new ResolveRequest(resolveId, composition, policy, inputFields, registration);
		return resolveRequest;
	}

	private final GatewayResolution resolve(ResolveRequest resolveRequest) {
		return gatewayChannel.apply(resolveRequest);
	}

	private final ExecRequest resolutionToExec(String requestId, GatewayResolution resolution) {
		ExecRequest execRequest = new ExecRequest(requestId, resolution.getCompositionGraph());
		return execRequest;
	}

	private final void deliverInputs(String execId, Map<String, SEDEObject> inputs) {
		for (String fieldname : inputs.keySet()) {
			SEDEObject field = inputs.get(fieldname);
			DataPutRequest putRequest = new DataPutRequest(execId, fieldname, field);
			getClientExecutor().put(putRequest);
		}
	}



	public static void logResult(Result result) {
		logger.debug("Received Result {}: {}: {}", result.getFieldname(), result.getResultData(), result.getResultData().getObject());
	}

	@Override
	public Executor getClientExecutor() {
		return executor;
	}


	static class ResultObserver implements Observer<ExecutionEnvironment> {
		private final String reqId;
		private final List<String> remainingResults;
		private final Consumer<Result> sedeObjectConsumer;

		ResultObserver(String reqId, List<String> remainingResults, Consumer<Result> sedeObjectConsumer) {
			this.reqId = reqId;
			this.remainingResults = new ArrayList<>(remainingResults);
			this.sedeObjectConsumer = sedeObjectConsumer;
		}

		@Override
		public boolean notifyCondition(ExecutionEnvironment env) {
			for(String field : remainingResults) {
				if(env.containsKey(field)){
					return true;
				}
			}
			return false;
		}

		@Override
		public void notification(ExecutionEnvironment env) {
			Iterator<String> remaining = remainingResults.iterator();
			while(remaining.hasNext()) {
				String fieldname = remaining.next();
				if(env.containsKey(fieldname)){
					Result result = new Result(reqId, fieldname, env.get(fieldname));
					sedeObjectConsumer.accept(result);
					remaining.remove();
				}
			}
		}

		@Override
		public boolean removeAfterNotification(ExecutionEnvironment executionEnvironment) {
			return false;
		}
	}
}
