package de.upb.sede.exec;

import de.upb.sede.exceptions.ExecutionIdOccupiedException;
import de.upb.sede.interfaces.IExecutor;
import de.upb.sede.procedure.*;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.util.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import java.util.List;
import java.util.Map;

public class Executor implements IExecutor{


	private static final Logger logger = LogManager.getLogger();

	private static final GraphJsonDeserializer deserializer = new GraphJsonDeserializer();

	private final ExecutionPool execPool;

	private final ResourceAllocator resourceAllocator;

	private final ExecutorConfiguration config;

	private final WorkerPool workerPool;

	public Executor() {
		this(new ExecutorConfiguration());
	}

	public Executor(ExecutorConfiguration execConfig) {
		this.execPool = new ExecutionPool(execConfig);
		this.config = execConfig;
		this.resourceAllocator = new ResourceAllocator(this.config.getAvailableResources());
		this.workerPool = new WorkerPool(execConfig.getThreadNumber(), execPool.openTasksSupplier());
		bindProcedureNames();
	}

	private final void bindProcedureNames(){
		workerPool.bindProcedure("Instruction", InstructionProcedure::new);
		workerPool.bindProcedure("ParseConstant", ParseConstantProcedure::new);
		workerPool.bindProcedure("AcceptData", AcceptDataProcedure::new);
		workerPool.bindProcedure("CastType", CastTypeProcedure::new);
		workerPool.bindProcedure("DeleteField", null); // TODO
		workerPool.bindProcedure("ServiceInstanceStorage", ServiceInstanceStorageProcedure::new);
		// send graph and transmit data needs to be specified..
	}

	public WorkerPool getWorkerPool() {
		return workerPool;
	}


	public Execution getExecution(String requestId) {
		return execPool.getExecution(requestId);
	}

	public Execution getOrCreateExecution(String requestId) {
		return execPool.getOrCreateExecution(requestId);
	}

	public ExecutionPool getExecPool(){
		return execPool;
	}

	@Override
	public synchronized void put(DataPutRequest dataPutRequest){
		Execution exec = getOrCreateExecution(dataPutRequest.getRequestID());
		exec.getEnvironment().put(dataPutRequest.getFieldname(), dataPutRequest.getData());
	}

	@Override
	public synchronized Execution exec(ExecRequest execRequest){
		String execId = execRequest.getRequestID();

		Execution exec = getOrCreateExecution(execRequest.getRequestID());

		deserializer.deserializeTasksInto(exec, execRequest.getCompositionGraph());

		execPool.startExecution(execId);
		logger.debug("Execution request {} started.", execRequest.getRequestID());
		return exec;
	}

	@Override
	public void loadServices(Object Services){
		// TODO
	}

	@Override
	public void interrupt(String executionId) {
		if(execPool.hasExecution(executionId)) {
			execPool.getExecution(executionId).interrupt();
		}
	}


	public void interruptAll() {
		execPool.forAll(Execution::interrupt);
	}


	@Override
	public Map<String, String> contactInfo() {
		JSONObject contactInfo = new JSONObject();
		contactInfo.put("id", getExecutorConfiguration().getExecutorId());
		return contactInfo;
	}
	@Override
	public ExecutorRegistration registration() {
		List<String> capibilities = getExecutorConfiguration().getExecutorCapabilities();
		List<String> supportedServices = getExecutorConfiguration().getSupportedServices();
		ExecutorRegistration registration = new ExecutorRegistration(contactInfo(), capibilities, supportedServices);
		return registration;
	}


	public ExecutorConfiguration getExecutorConfiguration() {
		return config;
	}


	/**
	 * Supplier of execution instances. <p>
	 * Given an execution-id this method creates a new Execution object without altering the state of the executor.
	 *
	 * @param executionId request id of the new execution.
	 *
	 * @return A fresh new execution object.
	 */
	public Execution provideWithExecution(String executionId) {
		return new Execution(executionId, config);
	}
}
