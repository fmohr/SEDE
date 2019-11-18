package de.upb.sede.exec;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.procedure.*;
import de.upb.sede.util.AsyncObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.interfaces.IExecutor;
import de.upb.sede.requests.DataPutRequest;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.util.Observable;
import de.upb.sede.util.Observer;

/**
 * Core implementation of an executor.
 */
public class Executor implements IExecutor {


	private static final Logger logger = LoggerFactory.getLogger(Executor.class);

	private final ExecutionPool execPool;

	private final ExecutorConfiguration config;

	private final WorkerPool workerPool;

	private final Observer<Task> taskWorkerEnqueuer;

	private final Observer<Execution> executionGarbageCollector;

	/**
	 * Contact info map.
	 * It is sent to the gateway and used to contact this executor.
	 * Can be modified by plugins.
	 */
	private final Map<String, Object> contactInfo = new HashMap<>();

	/*
	 * Hooks of the executor:
	 * These hooks are used by plugins who need to add functionality for certain events.
	 */

	/**
	 * This hook triggers when the executor has received a shutdown command. <br>
	 * The notification item is 'this' Executor.
	 */
	public final Observable<Executor> shutdownHook = new Observable<>();

	/**
	 * This hook triggers when a new Execution has been created through an {@link this#exec} invocation.<br>
	 * The notification item is the new Execution object.
	 */
	public final Observable<Execution> newExecutionHook = new Observable<>();

	public Executor(ExecutorConfiguration execConfig) {
		this.execPool = new ExecutionPool(execConfig);
		this.config = execConfig;
		this.workerPool = new WorkerPool(execConfig.getThreadNumber());
		this.taskWorkerEnqueuer = new AsyncObserver<>(Observer.lambda(t->true,  workerPool::processTask, t->false));
		this.executionGarbageCollector = new AsyncObserver<>(Observer.lambda(Execution::hasExecutionFinished,  // when an execution is done, .
				this::removeExecution,
				t -> false));
        contactInfo.put("id", getExecutorConfiguration().getExecutorId());
        contactInfo.put("groupId", getExecutorConfiguration().getGroupId());
		bindProcedureNames();
		logger.info(
				"Executor with id '{}' created.\n" +
				"Capabilities: {}\n" +
				"Supported services: {}\n" +
				"Contact-information: {}",
				execConfig.getExecutorId(), capabilities(), supportedServices(), contactInfo());
	}

	private final void bindProcedureNames(){
		workerPool.bindProcedure("Instruction", InstructionProcedure::new);
		workerPool.bindProcedure("ParseConstant", ParseConstantProcedure::new);
		workerPool.bindProcedure("AcceptData", AcceptDataProcedure::new);
		workerPool.bindProcedure("CastType", CastTypeProcedure::new);
		workerPool.bindProcedure("DeleteField", null); // TODO
		workerPool.bindProcedure("ServiceInstanceStorage", ServiceInstanceStorageProcedure::new);
		workerPool.bindProcedure("CollectErrors", CollectErrorsProcedure::new);
		// send graph and transmit data needs to be bounded from outside because
		// based on the type of this executor they require different implementations.
		// One can also rebind other procedures to change the behaviour of the executor.
	}

	public WorkerPool getWorkerPool() {
		return workerPool;
	}


	public Optional<Execution> getExecution(String execId) {
		return execPool.getExecution(execId);
	}

	public ExecutionPool getExecPool() {
		return execPool;
	}

	public void removeExecution(Execution execution) {
		if(!execPool.hasExecution(execution.getExecutionId())) {
			return;
		}
		if(execution.hasStarted()) {
			logger.debug("Removing execution: id={}, unfinished tasks={}", execution.getExecutionId(), execution.getUnfinishedTasksCount());
			execution.getMessenger().shutdownNow();
			execPool.removeExecution(execution);
			workerPool.removeExecution(execution);
		} else {
			logger.warn("Instructed to remove an unstarted execution = {}", execution.getExecutionId());
		}

	}

	@Override
	public void put(DataPutRequest dataPutRequest) {
		logger.debug("Executor has received putRequest: Field={}, Available={}, ExecutionId={}", dataPutRequest.getFieldname(), !dataPutRequest.isUnavailable(), dataPutRequest.getRequestID());
		String execId = dataPutRequest.getRequestID();
		Execution exec = execPool.getExecution(execId) .orElseGet(
				() -> {
					logger.warn("Put request received for a nonexistent execution. Creating an empty execution beforehand.");
					return execPool.getOrCreateExecution(execId);
				}
		);
		if(exec == null) {
			logger.warn("Put request is ignored because it is trying to put into" +
					" an already finished execution={}.", execId );
			return;
		}
		if(dataPutRequest.isUnavailable()) {
			/*
			 * The request indicates that the data is unavailable. (wont be delivered)
			 */
			exec.getEnvironment().markUnavailable(dataPutRequest.getFieldname());
		} else{
			/*
			 * The request contains the data:
			 */
			exec.getEnvironment().put(dataPutRequest.getFieldname(), dataPutRequest.getData());
		}
	}

	@Override
	public Execution exec(ExecRequest execRequest){
		String execId = execRequest.getRequestID();
		logger.debug("Executor has received execRequest: ExecutionId={}", execRequest.getRequestID());

		/*
		 * Retrieve the execution:
		 */
		Execution exec;
		/* First check if execution id is taken: */
		if(execPool.getExecution(execId).isPresent()) {
			logger.warn("Execution already exists: {}", execId);
		}
		exec = execPool.getOrCreateExecution(execId);
		if(exec == null) {
			logger.error("Exec request is ignored because the supplied exec id collided" +
					" with an already finished execution={}.", execId );
			throw new IllegalStateException("An execution with id=" + execId + " cannot be " +
					"created as it has already existed.");
		}
		exec.getRunnableTasksObservable().observe(taskWorkerEnqueuer);

		GraphJsonDeserializer.deserializeTasksInto(exec, execRequest.getCompositionGraph());

		exec.getState().observe(executionGarbageCollector);
		exec.start();
		/*
		  	Notify hooks:
		 */
		newExecutionHook.update(exec);
		logger.debug("Execution request {} started.", execRequest.getRequestID());
		return exec;
	}

	@Override
	public synchronized void interrupt(String executionId) {
		logger.debug("Executor has received interruptRequest: ExecutionId={}", executionId);
		if (execPool.hasExecution(executionId)) {
			Optional<Execution> toBeInterrupted = execPool.getExecution(executionId);
			if(toBeInterrupted.isPresent()){
				Execution target = toBeInterrupted.get();
				workerPool.interruptExec(target);
				target.interrupt();
			}
		}
	}



	public void interruptAll() {
		execPool.forAll(Execution::interrupt);
		execPool.forAll(execution -> {
			if(!execution.hasStarted() || execution.hasExecutionFinished()) {
				execPool.removeExecution(execution);
			}
		});
	}

	public Set<String> capabilities() {
		Set<String> capabilities = new TreeSet<>();
		/*
		 * Add implementation specific capabilities:
		 */
		// This is a java executor:
		capabilities.add("java");
		// The java implementation supports casting in place in transmit and accept nodes:
//		capabilities.add("cast_in_place");
		/*
		 * add capabilities specified in the configuration:
		 */
		capabilities.addAll(getExecutorConfiguration().getExecutorCapabilities());
		return capabilities;
	}

	public Set<String> supportedServices() {
		Set<String> services = new TreeSet<>();
		services.addAll(getExecutorConfiguration().getSupportedServices());
		/*
		 * add built-in services:
		 * (No built-in service.)
		 */
		return services;
	}

	public Map<String, Object> getModifiableContactInfo() {
		return contactInfo;
	}

	@Override
	public Map<String, Object> contactInfo() {
		return Collections.unmodifiableMap(contactInfo);
	}

	@Override
	public ExecutorRegistration registration() {
		List<String> capibilities = new ArrayList<>(capabilities());
		List<String> supportedServices = new ArrayList<>(supportedServices());
		Map<String, Object> contactInfo = contactInfo();
		ExecutorRegistration registration = new ExecutorRegistration
				(contactInfo, capibilities, supportedServices);
		return registration;
	}


	public ExecutorConfiguration getExecutorConfiguration() {
		return config;
	}

	@Override
	public void shutdown() {
		interruptAll();
		getWorkerPool().shutdown();
		shutdownHook.update(this);
	}

	public boolean deallocate(ServiceInstanceHandle serviceInstanceHandle) {
        String serviceFilePath = ServiceInstanceStorageProcedure.pathFor(getExecutorConfiguration().getServiceStoreLocation(), serviceInstanceHandle.getClasspath(), serviceInstanceHandle.getId());
        File serviceFile = new File(serviceFilePath);
        if(!serviceFile.exists() || serviceFile.isDirectory() || !serviceFile.canWrite()) {
            return false;
        } else {
            return serviceFile.delete();
        }
    }

}
