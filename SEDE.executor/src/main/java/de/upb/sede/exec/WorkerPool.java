package de.upb.sede.exec;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.procedure.Procedure;

public class WorkerPool {

	private static final Logger logger = LoggerFactory.getLogger(WorkerPool.class);

	private final ExecutorService workers;

	/**
	 * Use weak hashmap to have executions which are finished removed and avoid a memory leak.
	 */
	private final Map<Execution, List<Future>> executionFutureMap = new WeakHashMap<>();

	private final Map<String, Supplier<Procedure>> procedureSupplierMap = new HashMap<>();

	/**
	 * Comparator of runnable.  
	 */
	private final Comparator<Runnable> taskScheduler = Comparator.comparing(r -> ((FutureWithTask)r).getTask(),
			WorkerPool::lessRemainingTasks);

	WorkerPool(int workerNumber){
		workers = new PriorityThreadPool(workerNumber);
	}


	public synchronized void processTask(Task task){
		if(logger.isTraceEnabled()) {
			logger.trace("Task submitted to start: {}", task.getDescription());
		}

		Procedure procedure = procedureForTask(task.getTaskName());
		TaskRunner runner = new TaskRunner(task, procedure);
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
		if(executionFutureMap.containsKey(execution)){
			/**
			 * cancel/interrupt every task in the execution.
			 */
			for(Future f : executionFutureMap.get(execution)){
				f.cancel(true);
			}
			logger.info("{} interrupted.", execution.getExecutionId());
		}
	}

	private synchronized  void removeExec(Execution execution) {
		executionFutureMap.remove(execution);
	}

	public synchronized void bindProcedure(String procedureName, Supplier<Procedure> procedureSupplier) {
		procedureSupplierMap.put(procedureName, procedureSupplier);
	}

	public synchronized Supplier<Procedure> getBoundedProducer(String procedureName) {
		return procedureSupplierMap.get(procedureName);
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

	/**
	 * Returns true if the given execution is still running.
	 * Should only be used by tests.
	 */
	public synchronized boolean isExecutionOngoing(Execution exec) {
		if(executionFutureMap.containsKey(exec)) {
			return !executionFutureMap.get(exec).isEmpty();
		} else{
			return false;
		}
	}

	public int futueListSize() {
		return executionFutureMap.size();
	}

	public synchronized void removeExecution(Execution exec) {
		executionFutureMap.remove(exec);
	}


	private static class TaskRunner implements  Runnable {
		private Task task;
		private Procedure procedure;
		TaskRunner(Task task, Procedure procedure) {
			this.task = task;
			this.procedure = procedure;
		}
		public void run() {
			if(logger.isTraceEnabled())
				logger.trace("worker STARTED working on task: {}", task.getDescription());

			task.setStarted();

			try{
				if(!task.hasDependencyFailed()) {
					procedure.processTask(task);
				} else {
					procedure.processFail(task);
					task.setFailed();
				}
			} catch(Throwable ex) {
				logger.error("ERROR during {}:", task.getDescription(), ex);
				task.setError(new Exception((ex)));
				if(!task.hasFailed()){
					try{
						procedure.processFail(task);
						task.setFailed();
					} catch(Exception innerException){
						logger.error("ERROR during fail processing of {}:", task.getDescription(), innerException);
					}
				}
			}
			finally {
				task.setDone();
			}
			if(logger.isTraceEnabled())
				logger.trace("worker IS DONE working on task: {}", task.getDescription());
		}

		public Task getTask() {
			return task;
		}
	}



	/**
	 * Defines a comparator for Tasks.
	 * It sorts the given tasks by how many tasks remain in their executions.
	 * The less tasks remain the higher priority it will get:
	 */
	private static int lessRemainingTasks(Task t_x, Task t_y) {
		int x = t_x.getExecution().getUnfinishedTasksCount();
		int y = t_y.getExecution().getUnfinishedTasksCount();
		return Integer.compare(x, y);
	}

	private class PriorityThreadPool extends ThreadPoolExecutor {

		PriorityThreadPool(int workerNumber) {
			super(workerNumber, workerNumber,
					0L, TimeUnit.MILLISECONDS,
					new PriorityBlockingQueue<>(1000, taskScheduler));
		}
		@Override
		protected <T> RunnableFuture<T> newTaskFor(final Callable<T> callable) {
			throw new RuntimeException("Code Error.");
		}

		@Override
		protected <T> RunnableFuture<T> newTaskFor(final Runnable runnable,
												   final T value) {
			if (runnable instanceof TaskRunner)
				return new FutureWithTask<T>(runnable, value);
			else
				throw new RuntimeException("Code Error.");
		}
	}

	private static class FutureWithTask<T> extends  FutureTask<T> {
		private final Task task;
		public FutureWithTask(Runnable runnable, T result) {
			super(runnable, result);
			task = ((TaskRunner)runnable).getTask();
		}
		Task getTask(){
			return task;
		}
	}

}
