package de.upb.sede.client;


import de.upb.sede.core.ObjectDataField;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceField;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.Execution;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.util.FileUtil;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A client that makes requesting Server invocations 'easy'. The general idea is to do all service
 * calls via compositions.
 *
 * EasyClient offers 'with...' methods that allow building a request through chaining. E.g.:
 *
 * new EasyClient().withBody(body).withHost("localhost:80")
 *
 * After the desired request is built, use the 'dispatch' method to do a general purpose request.
 * For 'dispatch' a composition text has to be explicitly created.
 *
 * Alternatively EasyClient also offers methods that generate composition texts before making a
 * service request. E.g.: createService and invokeOneLineOperation.
 */
public final class EasyClient {

	private String composition = "";
	private Map<String, SEDEObject> inputMap = new HashMap<>();
	private ResolvePolicy policy = new ResolvePolicy();

	private Optional<String> executionId = Optional.empty();

	private final CoreClient innerClient;

	public EasyClient(CoreClient innerClient) {
		this.innerClient = innerClient;

	}

	public boolean checkInputs(final String... inputNamesToCheck) {
		for (String constructorArg : inputNamesToCheck) {
			if (!this.inputMap.containsKey(constructorArg)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Creates a single servicehandle.
	 *
	 * @param constructorArgNames
	 *          field names that are included as in the constructor as arguments.
	 */
	public ServiceInstanceHandle createOneService(final String serviceName, final String... constructorArgNames) throws ExecutionException, InterruptedException {
		// create a new composition:
		String comp = "out = " + serviceName + "::__construct(";
		comp += this.getCompositionArgsFromStringInputs(constructorArgNames);
		comp += ")";
		this.withComposition(comp);
		Map<String, Result> result = this.dispatch().get();
		if (result.containsKey("out")) {
			return result.get("out").getServiceInstanceHandle();
		} else {
			throw new RuntimeException("CODING BUG: This shouldn't happen.");
		}
	}

	public Future<Map<String, Result>> dispatch() throws ExecutionException {
		RunRequest runRequest = new RunRequest(composition, policy, inputMap);
		if(executionId.isPresent()) {
			runRequest.setRequestId(executionId.get());
		}
		final CoreClient.MapResultConsumer results = new CoreClient.MapResultConsumer();
		try{
			String executionId = innerClient.run(runRequest, results);
			RunFuture futureObj = new RunFuture(executionId, results);
			return futureObj;
		} catch(RuntimeException ex) {
			throw new ExecutionException(ex);
		}
	}

	public Result invokeOneLineOperation(final String context, final String methodName, final String... methodArgNames) throws InterruptedException, ExecutionException {
		// create a new composition:
		String comp = "out = " + context + "::" + methodName + "(";
		comp += this.getCompositionArgsFromStringInputs(methodArgNames);
		comp += ")";
		this.withComposition(comp);
		return this.dispatch().get().get("out");
	}

	/**
	 * Appends a construct operation to the composition. Requires a host to be set before.
	 *
	 * @param outFieldName
	 *          the field name that is on the left side of '='.
	 * @param classpath
	 *          classpath of the service to be created.
	 * @param constructorArgFieldNames
	 *          Parameter names that are used in the constructor.
	 */
	public EasyClient withAddedConstructOperation(final String outFieldName, final String classpath, final String... constructorArgFieldNames) {
		String compositionLine = outFieldName + "=" + classpath + "::__construct(";
		compositionLine += this.getCompositionArgsFromStringInputs(constructorArgFieldNames);
		compositionLine += ");\n";
		composition += (compositionLine);
		return this;
	}

	/**
	 * Appends a method operation to the composition. Additionally, if the composition was empty before
	 * adding the created operation, the innerHandle will be set to the service retrieved with
	 * 'variableFieldName' from the keyword argument pool. If the service has not been added with
	 * 'withKeywordArgument' before, nothing will happen.
	 *
	 * @param outFieldName
	 *          the field name that is on the left side of '='.
	 * @param contextString
	 *          field name of the service variable on whome the operation is executed.
	 * @param methodName
	 *          The name of the method to be executed.
	 * @param methodArgFieldNames
	 *          Parameter names that are used in the method invocation.
	 *
	 */
	public EasyClient withAddedMethodOperation(final String outFieldName, final String contextString, final String methodName, final String... methodArgFieldNames) {
		String compositionLine = "";
		if(outFieldName != null) {
			compositionLine += outFieldName + "=";
		}
		compositionLine += contextString + "::" + methodName + "(";
		compositionLine += this.getCompositionArgsFromStringInputs(methodArgFieldNames);
		compositionLine += ");";
		composition += (compositionLine);
		return this;
	}

	public EasyClient withInputs(final Map<String, SEDEObject> inputs) {
		this.inputMap.putAll(inputs);
		return this;
	}

	public EasyClient withServices(final Map<String, ServiceInstanceHandle> services) {
		for(String fieldname : services.keySet()) {
			ServiceInstanceHandle serviceInstanceHandle = services.get(fieldname);
			withArgument(fieldname, new ServiceInstanceField(serviceInstanceHandle));
		}
		return this;
	}

	public EasyClient withComposition(final String compostionText) {
		this.composition = compostionText;
		return this;
	}

	public EasyClient withCompositionFile(final String filePath) {
		String composition = FileUtil.readFileAsString(filePath);
		this.composition = composition;
		return this;
	}

	public EasyClient withArgument(final String keyword, final SEDEObject data) {
		Objects.requireNonNull(keyword);
		Objects.requireNonNull(data);
		this.inputMap.put(keyword, data);
		return this;
	}

	public EasyClient withArgument_StringList(final String keyword, final String... argValues) {
		return this.withArgument_StringList(keyword, Arrays.asList(argValues));
	}
	public EasyClient withArgument_StringList(final String keyword, final List<String> argValues) {
		Objects.requireNonNull(keyword);
		Objects.requireNonNull(argValues);
		SEDEObject sedeObject = new ObjectDataField("builtin.List", argValues);
		return this.withArgument(keyword, sedeObject);
	}

	public void withId(String executionId) {
		this.executionId = Optional.ofNullable(executionId);
	}

	public String getCurrentCompositionText() {
		return this.composition;
	}

	// _____ UTILITY _____

	private String getCompositionArgsFromStringInputs(final String... argNames) {

		if (argNames.length > 0) {
			String comp = "";
			int index = 1;
			for (String constructorArg : argNames) {
				comp += ",i" + index + "=" + constructorArg;
				index++;
			}
			comp = "{" + comp.substring(1) + "}"; // first comma is too much. Look at the
			// loop
			return comp;
		}
		else {
			return "";
		}
	}

	public ResolvePolicy getPolicy() {
		return policy;
	}


	class RunFuture implements  Future<Map<String, Result>> {
		final String executionId;
		final Map<String, Result> resultMap;
		RunFuture(String executionId, Map<String, Result> resultMap) {
			this.executionId = executionId;
			this.resultMap = resultMap;
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			innerClient.interrupt(executionId);
			return true; // glass half full, eh?
		}

		@Override
		public boolean isCancelled() {
			Optional<Execution> execution = innerClient.getClientExecutor().getExecPool().getExecution(executionId);
			if(execution.isPresent()){
				return execution.get().hasExecutionBeenInterrupted();
			}
			else {
				return true; // actually it could have just finished.
			}
		}

		@Override
		public boolean isDone() {
			Optional<Execution> execution = innerClient.getClientExecutor().getExecPool().getExecution(executionId);
			if(execution.isPresent()){
				return execution.get().hasExecutionFinished();
			}
			else {
				return true;
			}
		}

		@Override
		public Map<String, Result> get() throws InterruptedException, ExecutionException {
			innerClient.join(executionId, true);
			return resultMap;
		}

		@Override
		public Map<String, Result> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			innerClient.join(executionId, true, timeout, unit);
			return resultMap;
		}
	}

}