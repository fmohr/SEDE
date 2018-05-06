package de.upb.sede.exec;

import de.upb.sede.exceptions.ExecutionIdOccupiedException;
import de.upb.sede.interfaces.IExecutor;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;

public abstract class Executor implements IExecutor{

	private static final GraphJsonDeserializer deserializer = new GraphJsonDeserializer();

	private final ExecutionPool execPool;

	private final ResourceAllocator resourceAllocator;

	private final ExecutorConfiguration config;


	public Executor(ExecutorConfiguration execConfig, ExecutionPool execPool) throws Exception {
		this.execPool = execPool;
		this.config = execConfig;
		this.resourceAllocator = new ResourceAllocator(this.config.getAvailableResources());
	}

	public Execution getExecWithId(String requestId) {
		return execPool.getOrCreateExecution(requestId);
	}

	@Override
	public void put(DataPutRequest dataPutRequest){
		Execution exec = getExecWithId(dataPutRequest.getRequestID());

	}

	@Override
	public void exec(ExecRequest execRequest){
		String execId = execRequest.getRequestID();

		if(execPool.hasExecution(execId)){
			throw new ExecutionIdOccupiedException(execId);
		}
		Execution exec = getExecWithId(execRequest.getRequestID());
		deserializer.deserializeTasksInto(exec, execRequest.getCompositionGraph());
	}

	@Override
	public void loadServices(Object Services){
		// TODO
	}

	public ExecutionPool getExecPool(){
		return execPool;
	}

}
