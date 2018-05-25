package de.upb.sede.exec;

import de.upb.sede.procedure.Procedure;
import de.upb.sede.util.Observer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

public class WorkerPool {

	private static final Logger logger = LogManager.getLogger();

	private final ExecutorService workers;

	/**
	 * Use weak hashmap to have executions which are finished removed and avoid a memory leak.
	 */
	private final Map<Execution, List<Future>> executionFutureMap = new WeakHashMap<>();

	private final Map<String, Supplier<Procedure>> procedureSupplierMap = new HashMap<>();


	WorkerPool(int workerNumber){
		workers = Executors.newFixedThreadPool(workerNumber);
	}


	public synchronized void processTask(Task task){
		if(logger.isTraceEnabled()) {
			logger.trace("{} submitted: ", task.toString(), task.toDebugString());
		}

		Procedure procedure = procedureForTask(task.getTaskName());
		Runnable runner;
		if(task.hasFailed()) {
			runner = new OnFailRunner(task, procedure);
		} else if(!task.hasStarted()) {
			runner = new ProcedureRunner(task, procedure);
		} else{
			logger.error("Task {} has been submitted to run." +
					" But it hasn't failed and has already started to run:\n{}", task.toString(), task.toDebugString());
			return; // TODO what to do here?
		}
		Future future = workers.submit(runner);
		addFuture(task.getExecution(), future);
	}



	private void addFuture(Execution exec, Future<?> future){
		if(!executionFutureMap.containsKey(exec)){
			List<Future> futureList = new ArrayList<>();
			futureList.add(future);
			executionFutureMap.put(exec, futureList);
		}  else{
			executionFutureMap.get(exec).add(future);
		}
	}


	public synchronized void interruptExec(Execution execution) {
		logger.info("{} interrupted.", execution.getExecutionId());
		if(executionFutureMap.containsKey(execution)){
			/**
			 * cancel/interrupt every task in the execution.
			 */
			for(Future f : executionFutureMap.get(execution)){
				f.cancel(true);
			}
		}
	}

	private synchronized  void removeExec(Execution execution) {
		executionFutureMap.remove(execution);
	}

	public synchronized void bindProcedure(String procedureName, Supplier<Procedure> procedureSupplier) {
		procedureSupplierMap.put(procedureName, procedureSupplier);
	}

	private Procedure procedureForTask(String taskName) {
		if(procedureSupplierMap.containsKey(taskName)){
			return procedureSupplierMap.get(taskName).get();
		}
		else {
			throw new RuntimeException("Task \"" + taskName + "\" was not bound to a procedure supplier.");
		}
	}

	public void shutdown() {
		workers.shutdown();
	}

	private static class ProcedureRunner implements  Runnable {
		private Task task;
		private Procedure procedure;
		ProcedureRunner(Task task, Procedure procedure) {
			this.task = task;
			this.procedure = procedure;
		}
		public void run() {
			if(logger.isTraceEnabled())
				logger.trace("worker STARTED working on task: {}", task.toDebugString());
			task.setStarted();
			try{
				procedure.process(task);
			} catch(Exception ex) {
				task.setError(ex);
				task.setFailed();
				logger.error("ERROR during {}:\n", task.toDebugString(), ex);
			}
			finally {
				task.isDoneRunning();
			}
			if(logger.isTraceEnabled())
				logger.trace("worker IS DONE working on task: {}", task.toDebugString());
		}
	}


	private static class OnFailRunner implements  Runnable {
		private Task task;
		private Procedure procedure;
		OnFailRunner(Task task, Procedure procedure) {
			this.task = task;
			this.procedure = procedure;
		}
		@Override
		public void run() {
			if(!task.hasFailed()){
				logger.error("BUG: fail run on not failed task");
			}
			try{
				procedure.processFail(task);
			} catch(Exception ex) {
				logger.error("ERROR during process fail of {}:\n", task.toDebugString(), ex);
			}
		}
	}
}
