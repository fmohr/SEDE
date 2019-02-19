package de.upb.sede.exec;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.config.ExecutorConfiguration;

public class ExecutionPool {


	private static final Logger logger = LoggerFactory.getLogger(ExecutionPool.class);
	/**
	 * be aware that the implementation of the map is not thread safe.
	 * So don't expose the map itself and only operate on it in synchronous methods.
	 */
	private final Map<String, Execution> execMap = new ConcurrentHashMap<>();


	private final ExecutorConfiguration executorConfiguration;


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

	public synchronized boolean hasExecution(String execId) {
		return execMap.containsKey(execId);
	}

	public synchronized void removeExecution(Execution execution) {
		if(hasExecution(execution.getExecutionId())){
			logger.debug("Removing execution from pool: {}", execution.getExecutionId());
			execMap.remove(execution.getExecutionId());
		}
	}

	private Execution createExecution(String execId){
		return new Execution(execId, executorConfiguration);
	}


	public synchronized  void forAll(Consumer<Execution> executionConsumer) {
		Set<String> executions = new HashSet<>(execMap.keySet());
		for(String execId : executions) {
			executionConsumer.accept(execMap.get(execId));
		}
	}

	public synchronized Optional<Execution> getExecution(String execId) {
		return Optional.ofNullable(execMap.get(execId));
	}
}
