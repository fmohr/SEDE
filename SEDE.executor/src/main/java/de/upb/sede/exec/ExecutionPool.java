package de.upb.sede.exec;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.util.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExecutionPool {


	private static final Logger logger = LogManager.getLogger();
	/**
	 * be aware that the implementation of the map is not thread safe.
	 * So don't expose the map itself and only operate on it in synchronous methods.
	 */
	private final Map<String, Execution> execMap = new HashMap<>();


	private final ExecutorConfiguration executorConfiguration;

	private Function<String, Execution> executionSupplier;

	ExecutionPool(ExecutorConfiguration executorConfiguration){
		this.executorConfiguration = executorConfiguration;
		setExecutionSupplier(this::createExecution);
	}

	synchronized Execution getOrCreateExecution(String execId) {
		Execution exec = execMap.get(execId);
		if(exec == null) {
			logger.debug("{} created a new execution: {}", executorConfiguration.getExecutorId(),execId);
			/*
			 * The given id doesn't have any execution assigned to it.
			 * Create a new Execution for the given request id:
			 */
			exec = executionSupplier.apply(execId);
			execMap.put(execId, exec);
		}
		return exec;
	}

	public synchronized boolean hasExecution(String execId) {
		return execMap.containsKey(execId);
	}

	public synchronized void removeExecution(Execution execution) {
		if(hasExecution(execution.getExecutionId())){
			execMap.remove(execution.getExecutionId());
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

	public synchronized Optional<Execution> getExecution(String execId) {
		return Optional.ofNullable(execMap.get(execId));
	}

	public void setExecutionSupplier(Function<String, Execution> executionForIdSupplier) {
		this.executionSupplier = executionForIdSupplier;
	}
}
