package de.upb.sede.exec;

import de.upb.sede.util.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExecutionPool {


	private static final Logger logger = LogManager.getLogger();
	/**
	 * be aware that the implementation of the map is not thread safe.
	 * So don't expose the map itself and only operate on it in synchronous methods.
	 */
	private final Map<String, Execution> execMap = new LinkedHashMap<>();

	private Supplier<Task> scheduler = new FIFO();

	private final TaskProvider taskProvider = new TaskProvider();

	private final ExecutorConfiguration executorConfiguration;

	private final Observer<Execution> executionObserver = Observer.lambda(Execution::hasExecutionFinished,  // when an execution is done, ..
			exec -> removeExecution(exec.getExecutionId())); // remove it.

	private final Observer<Execution> readyToRunObserver = Observer.lambda(Execution::hasWaitingTasks,
			e -> {
				synchronized (taskProvider) {
					taskProvider.notifyAll();
				}
			}, Execution::hasExecutionFinished);

	ExecutionPool(ExecutorConfiguration executorConfiguration){
		this.executorConfiguration = executorConfiguration;
	}

	synchronized Execution getOrCreateExecution(String execId) {
		Execution exec = execMap.get(execId);
		if(exec == null) {
			logger.debug("{} created a new execution: {}", executorConfiguration.getExecutorId(),execId);
			/*
			 * The given id doesn't have any execution assigned to it.
			 * Create a new Execution for the given request id:
			 */
			exec = createExecution(execId);
			execMap.put(execId, exec);
		}
		return exec;
	}

	synchronized  void startExecution(String execId) {
		Execution exec = execMap.get(execId);
		if(exec!=null){
			exec.getState().observe(readyToRunObserver);
			exec.getState().observe(executionObserver);
		}
	}

	public synchronized boolean hasExecution(String execId) {
		return execMap.containsKey(execId);
	}

	public synchronized void removeExecution(String execId) {
		if(hasExecution(execId)){
			execMap.remove(execId);
		}
	}

	private Execution createExecution(String execId){
		return new Execution(execId, executorConfiguration);
	}


	public synchronized  void forAll(Consumer<Execution> executionConsumer) {
		for(String execId : execMap.keySet()) {
			executionConsumer.accept(execMap.get(execId));
		}
	}

	public synchronized  Execution getExecution(String requestId) {
		return execMap.get(requestId);
	}

	public Supplier<Task> openTasksSupplier(){
		return taskProvider;
	}

	private class TaskProvider implements Supplier<Task> {

		@Override
		public synchronized Task get() {
			Task t;
			while(( t = scheduler.get())==null){
				try{
					this.wait();
				} catch(InterruptedException ex) {}
			}
			return t;
		}
	}
	private class FIFO implements Supplier<Task> {

		@Override
		public synchronized Task get() {
			for(String execId : execMap.keySet()){
				Execution execution = execMap.get(execId);
				if(execution.hasWaitingTasks()){
					return execution.getWaitingTasks().iterator().next();
				}
			}
			return null;
		}
	}
}
