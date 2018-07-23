package de.upb.sede.client;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Executor;
import de.upb.sede.interfaces.ICoreClient;
import de.upb.sede.requests.*;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class CoreClient implements ICoreClient{

	protected static final Logger logger = LogManager.getLogger();

	private final Executor executor;

	private final Function<ResolveRequest, GatewayResolution> gatewayChannel;

	private Optional<BiConsumer<String, String>> dotGraphConsumer = Optional.empty();

	public CoreClient(Executor executor, Function<ResolveRequest, GatewayResolution> gatewayChannel)  {
		this.executor = executor;
		this.gatewayChannel = gatewayChannel;
	}

	@Override
	public Map<String, Result> blockingRun(RunRequest runRequest) {
		MapResultConsumer resultMap = new MapResultConsumer();
		String execId = run(runRequest, resultMap);

		join(execId, true);
		return resultMap;
	}

	@Override
	public void join(String requestId, boolean interruptExecution) {
		// only uses timeout when its above 0.
		this.join(requestId, interruptExecution, -1, TimeUnit.DAYS);
	}

	@Override
	public void join(String requestId, boolean interruptExecution, long timeout, TimeUnit timeUnit) {
		Optional<Execution> execution = executor.getExecution(requestId);
		if(!execution.isPresent()) {
			return;
		}
		final Semaphore executionIsFinished = new Semaphore(0);
		final Object dummy = new Object();

		Observer<Execution> executionDoneObserver = Observer.lambda(Execution::hasExecutionFinished, exec -> {
			executionIsFinished.release();
		});

		execution.get().getState().observe(executionDoneObserver);
		try {
			if(timeout > 0 )
				executionIsFinished.tryAcquire(timeout, timeUnit);
			else
				executionIsFinished.acquire();
		} catch (InterruptedException e) {
			if (interruptExecution) {
				interrupt(requestId);
			}
		}
	}


	@Override
	public String run(RunRequest runRequest, Consumer<Result> resultConsumer) {
		if(resultConsumer == null) {
			/*
				In case of null just log the results.
			 */
			resultConsumer = CoreClient::logResult;
		}
		 else if(logger.isDebugEnabled()) {
			resultConsumer = resultConsumer.andThen(CoreClient::logResult);
		}
		String requestId = runRequest.getRequestID();

		/* make a resolve request from the run request */
		ResolveRequest resolveRequest = runToResolve(runRequest, requestId);

		/* If there is a consumer of the debug dot file make sure the gateway send the dot file back */
		if(dotGraphConsumer.isPresent()){
			resolveRequest.getPolicy().setToReturnDotGraph(true);
		}

		/* let a gateway resolve this request */
		GatewayResolution resolution = resolve(resolveRequest);

		/* Give the dot graph to the consumer */
		if(dotGraphConsumer.isPresent()){
			dotGraphConsumer.get().accept(requestId, resolution.getDotSvg());
		}

		/* Create a execution request from the resolution and run it on the client executor */
		ExecRequest execRequest = resolutionToExec(requestId, resolution);
		Execution execution = getClientExecutor().exec(execRequest);
		/* hand down the inputs for the execution to the executor */
		deliverInputs(requestId, runRequest.getInputs());

		List<String> resultFields = resolution.getReturnFields();
		logger.debug("waiting for {}", resultFields);
		ResultObserver resultObserver = new ResultObserver(requestId, resultFields, resultConsumer);
		execution.getEnvironment().observe(resultObserver);

		logger.debug("Run-Request {} started.", requestId);
		return requestId;
	}

	@Override
	public void interrupt(String requestId) {
		getClientExecutor().interrupt(requestId);
	}

	public final ResolveRequest runToResolve(RunRequest runRequest, String resolveId) {
		String composition = runRequest.getComposition();
		ResolvePolicy policy = runRequest.getPolicy();

		ExecutorRegistration registration = getClientExecutor().registration();

		Map<String, SEDEObject> inputs = runRequest.getInputs();
		InputFields inputFields = InputFields.fromMap(inputs);

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
		if(!result.hasFailed())
			logger.debug("Received Result {}: {}", result.getFieldname(), result.getResultData());
		else
			logger.debug("Result unavailable {}", result.getFieldname());
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
				if(env.isUnavailable(field)) {
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
				} else if(env.isUnavailable(fieldname)) {
					Result result = Result.failed(reqId, fieldname);
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

	public static class MapResultConsumer extends ConcurrentHashMap<String, Result> implements Consumer<Result> {

		@Override
		public void accept(Result result) {
			this.put(result.getFieldname(), result);
		}
	}

	public void setDotGraphConsumer(BiConsumer<String, String> executionIdDotConsumer){
		dotGraphConsumer = Optional.of(executionIdDotConsumer);
	}

	public void writeDotGraphToDir(final String directoryPath) {
		boolean needsSlash = !directoryPath.endsWith("/");
		this.setDotGraphConsumer((executionId, svgString) -> {
			String pathToDotGraph = directoryPath + (needsSlash ? "/" : "")
					+ executionId + ".resolution.svg";
			FileUtil.writeStringToFile(pathToDotGraph, svgString);
		});
	}
}
