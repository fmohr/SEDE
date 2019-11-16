package de.upb.sede.client;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.util.AsyncObserver;
import de.upb.sede.util.JsonSerializable;
import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.Execution;
import de.upb.sede.exec.ExecutionEnvironment;
import de.upb.sede.exec.Executor;
import de.upb.sede.interfaces.ICoreClient;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Observer;

public class CoreClient implements ICoreClient{

	protected static final Logger logger = LoggerFactory.getLogger(CoreClient.class);

	public final static String ERROR_COLLECTION_FIELDNAME = "__execution_errors_%s";

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

		Observer<Execution> executionDoneObserver = new AsyncObserver<Execution>(
				Observer.lambda(Execution::hasExecutionFinished, exec -> executionIsFinished.release()),
				execution.get().getMessenger());

		execution.get().getState().observe(executionDoneObserver);
		try {
			if(timeout > 0 )
				executionIsFinished.tryAcquire(timeout, timeUnit);
			else
				executionIsFinished.acquire();
		} catch (InterruptedException e) {
			logger.info("CoreClient was interrupted {} while joining execution={}.", requestId);
			if (interruptExecution) {
				logger.info("Propagate interruption to execution={}", requestId);
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
			/*
				If debug is enabled enforce logging:
			 */
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
		/*
		 * Results need to include error collection of this executor:
		 */
		resultFields.add(getErrorFieldName());

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
				} else if(env.isUnavailable(fieldname)) {
					Result result = Result.failed(reqId, fieldname);
					sedeObjectConsumer.accept(result);
				}
			}
		}

		@Override
		public boolean removeAfterNotification(ExecutionEnvironment executionEnvironment) {
			return false;
		}
	}

	public static class MapResultConsumer extends ConcurrentHashMap<String, Result> implements Consumer<Result> {

		public MapResultConsumer() {

		}

		@Override
		public void accept(Result result) {
			this.put(result.getFieldname(), result);
		}
	}

	public void setDotGraphConsumer(BiConsumer<String, String> executionIdDotConsumer){
		dotGraphConsumer = Optional.of(executionIdDotConsumer);
	}

	public String getErrorFieldName() {
		return String.format(ERROR_COLLECTION_FIELDNAME, executor.contactInfo().get("id"));
	}

	public void assertErrorFreeRun(Map<String, Result> resultMap) throws Exception {
		if(resultMap.containsKey(this.getErrorFieldName())) {
			/*
			 * error collection maps executor_ids onto a it's executor specific error collection.
			 * An executor specific error collection maps from task description onto the stacktrace of their runtime-exception.
			 * If an executor specific error collection is empty it means that the executor didn't have any errors.
			 */
			Map<String, Map<String, String>> errorCollection = resultMap.get(this.getErrorFieldName()).getResultData().getDataField();
			if(!errorCollection.values().stream().allMatch(Map::isEmpty)) {
				/*
				 * Recreate the stack trace
				 */
				StringBuilder errorBuilder = new StringBuilder();
				for(String executorId : errorCollection.keySet()) {
					Map<String, String> executorErrors = errorCollection.get(executorId);
					if(executorErrors.isEmpty()) {
						continue;
					}
					errorBuilder.append(executorId).append(" errors: \n");
					for(String taskDescription : executorErrors.keySet()){
						String stackTrace = executorErrors.get(taskDescription);
						errorBuilder.append("Error during ").append(taskDescription).append(". Stack Trace: ") .append(stackTrace).append("\n");
					}
				}
				throw new Exception(errorBuilder.toString());
			}
		} else {
			throw new Exception("Results doesn't contain error collection: " + this.getErrorFieldName());
		}
	}

    /**
     *
     * @param directoryPath
     */
	public void writeDotGraphToDir(final String directoryPath) {
		boolean needsSlash = !directoryPath.endsWith("/");
		this.setDotGraphConsumer((executionId, svgString) -> {
			String pathToDotGraph = directoryPath + (needsSlash ? "/" : "")
					+ executionId + ".resolution.svg";
			FileUtil.writeStringToFile(pathToDotGraph, svgString);
		});
	}


	/**
	 * Deallocates all given service instance handles.
	 * If the service handle is on this executor (clients executor) it will simply be removed.
	 * Else if the service handle is on an external executor the given consumer will be called in batches of service instance handles.
	 *
     * @param externalDeallocator Call-back function that is called in batches with service handles of a common external executor.
	 *
	 */
	private void deallocateAll(List<ServiceInstanceHandle> handles, Consumer<List<ServiceInstanceHandle>> externalDeallocator) {
	    if(Objects.requireNonNull(handles).isEmpty()) {
	        throw new IllegalArgumentException("Service handle list to be removed is empty.");
        }
	    logger.info("Client makes deallocation requests for {} many services.", handles.size());
	    Map<String, List<ServiceInstanceHandle>> externalServices = new HashMap<>();
        for(ServiceInstanceHandle handle : handles) {
            if(handle.getExecutorId().equals(getClientExecutor()
                .getExecutorConfiguration().getExecutorId())) {
                /*
                 * Service handle is pointing to a service serialized on the client executor.
                 *
                 * Make direct deallocate call.
                 */
                logger.debug("Deallocating local service instance {}/{}.",
                    handle.getClasspath(), handle.getId());
                boolean deallocate = getClientExecutor().deallocate(handle);
                if(!deallocate) {
                    logger.warn("Couldn't deallocate local service instance {}/{}",
                        handle.getClasspath(), handle.getId());
                }
            } else  {
                /*
                 * Service handle is pointing at a service on another executor.
                 *
                 * Collect all of the service handles of that executor and make the dealloc call in a batch:
                 */
                externalServices.computeIfAbsent(handle.getExecutorId(), executorId -> new ArrayList<>())
                        .add(handle);
            }
        }
        /*
         * Forward to external deallocator.
         */
        externalServices.values().forEach(externalDeallocator);
    }

    /**
     * Builds on top of the above deallocateAll method.
     * It relies on a function that can provide the http address of external executors.
     */
    private void deallocateAll(List<ServiceInstanceHandle> handles, Function<String, Optional<String>> executorAddressResolver) {
        deallocateAll(handles, externalSIList -> {
            if (externalSIList.isEmpty()) {
                logger.error("BUG: empty service instance list.");
                return;
            }
            String executorId = externalSIList.get(0).getExecutorId();
            String executorHostAddress;
            Optional<String> optExecutorHostAddress = executorAddressResolver.apply(executorId);
            if(!optExecutorHostAddress.isPresent()) {
                logger.error("While deallocating the following services, couldn't resolve the address of the executor with id `{}`: \n[{}]",
                    executorId, handles.stream()
                        .map(ServiceInstanceHandle::toString)
                        .collect(Collectors.joining(", ")));
                return;
            }
            executorHostAddress = optExecutorHostAddress.get();
            HttpURLConnectionClientRequest deallocRequest;
            deallocRequest = new HttpURLConnectionClientRequest(executorHostAddress + "/deallocate");
            String payload = JSONArray.toJSONString(externalSIList.stream().map(JsonSerializable::toJson).collect(Collectors.toList()));
            String deallocResult = deallocRequest.send(payload);
            if (deallocResult != null && !deallocResult.isEmpty()) {
                logger.warn("Error while making http dealloc call to external executor: \n{}", deallocResult);
            }
        });
    }

    public void deallocateAll(List<ServiceInstanceHandle> handles, String gatewayHostAddress) {
        deallocateAll(handles, (String executorId) -> {
            String getContactInfoRequestPath = "/executors/contact-info";
            HttpURLConnectionClientRequest deallocRequest;
            deallocRequest = new HttpURLConnectionClientRequest(gatewayHostAddress + getContactInfoRequestPath);
            String payload = executorId;
            String contactInfoString = deallocRequest.send(payload);
            Map contactInfo;
            try {
                JSONParser parser = new JSONParser();
                contactInfo = (Map) parser.parse(contactInfoString);
            } catch (ParseException e) {
                String errorMessage = contactInfoString;
                logger.error("Error while resolving the address of executor `{}` from gateway at `{}`. Return message: \n{}",
                    executorId, gatewayHostAddress, errorMessage);
                return Optional.empty();
            }
            String exectorHostAddress = (String) contactInfo.get("host-address");
            if(exectorHostAddress == null) {
                logger.error("The gateway at `{}` returned a contact-info of the executor `{}` that doesn't have a host address field.", gatewayHostAddress, executorId);
            }
            return Optional.ofNullable(exectorHostAddress);
        });


    }

}
