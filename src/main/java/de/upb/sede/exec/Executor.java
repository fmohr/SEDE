package de.upb.sede.exec;

import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exceptions.ExecutionIdOccupiedException;
import de.upb.sede.interfaces.IExecutor;
import de.upb.sede.procedure.InstructionProcedure;
import de.upb.sede.procedure.ParseConstantProcedure;
import de.upb.sede.procedure.ReceiveDataProcedure;
import de.upb.sede.procedure.SendInstanceStorageProcedure;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.util.Observer;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.UUID;

public class Executor implements IExecutor{

	private static final GraphJsonDeserializer deserializer = new GraphJsonDeserializer();

	private final ExecutionPool execPool;

	private final ResourceAllocator resourceAllocator;

	private final ExecutorConfiguration config;

	private final WorkerPool workerPool;

	private final Observer<Task> taskWorkerEnqueuer;


	public Executor(ExecutorConfiguration execConfig) throws Exception {
		this.execPool = new ExecutionPool(execConfig);
		this.config = execConfig;
		this.resourceAllocator = new ResourceAllocator(this.config.getAvailableResources());
		this.workerPool = new WorkerPool(execConfig.getThreadNumber());
		this.taskWorkerEnqueuer = Observer.lambda(t->true,  workerPool::processTask, t->false);
		bindProcedureNames();
	}

	private final void bindProcedureNames(){
		workerPool.bindProcedure("Instruction", InstructionProcedure::new);
		workerPool.bindProcedure("ParseConstant", ParseConstantProcedure::new);
		workerPool.bindProcedure("AcceptData", ReceiveDataProcedure::new);
		workerPool.bindProcedure("CastType", null); // TODO
		workerPool.bindProcedure("DeleteField", null); // TODO
		workerPool.bindProcedure("ServiceInstanceStorage", SendInstanceStorageProcedure::new);
		// send graph and transmit data needs to be specified..
	}

	public WorkerPool getWorkerPool() {
		return workerPool;
	}

	public Execution getExecWithId(String requestId) {
		return execPool.getOrCreateExecution(requestId);
	}

	public ExecutionPool getExecPool(){
		return execPool;
	}

	@Override
	public void put(DataPutRequest dataPutRequest){
		Execution exec = getExecWithId(dataPutRequest.getRequestID());
		exec.getExecutionEnvironment().put(dataPutRequest.getFieldname(), dataPutRequest.getData());
	}

	@Override
	public void exec(ExecRequest execRequest){
		String execId = execRequest.getRequestID();

		if(execPool.hasExecution(execId)){
			throw new ExecutionIdOccupiedException(execId);
		}
		Execution exec = getExecWithId(execRequest.getRequestID());
		deserializer.deserializeTasksInto(exec, execRequest.getCompositionGraph());

		exec.getNewTasksObservable().observe(taskWorkerEnqueuer);
	}

	@Override
	public void loadServices(Object Services){
		// TODO
	}

	protected Map<String, String> getContactInfo() {
		JSONObject contactInfo = new JSONObject();
		contactInfo.put("id", getExecutorConfiguration().getExecutorId());
		return contactInfo;
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
