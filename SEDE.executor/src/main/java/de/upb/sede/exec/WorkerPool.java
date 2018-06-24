package de.upb.sede.exec;

import de.upb.sede.procedure.Procedure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class WorkerPool {

	private static final Logger logger = LogManager.getLogger();

	private final ExecutorService workers;

	/**
	 * Use weak hashmap to have executions which are finished removed and avoid a memory leak.
	 */
	private final Map<Execution, List<Future>> executionFutureMap = new WeakHashMap<>();

	private final Map<String, Supplier<Procedure>> procedureSupplierMap = new HashMap<>();

	/**
	 * Comparator of runnable. Used by the 
	 */
	private final Comparator<Runnable> taskScheduler = Comparator.comparing(r -> ((FutureWithTask)r).getTask(),
			WorkerPool::lessRemainingTasks);

	WorkerPool(int workerNumber){
		workers = new PriorityThreadPool(workerNumber);
	}


	public synchronized void processTask(Task task){
		if(logger.isTraceEnabled()) {
			logger.trace("{} submitted: ", task.toString(), task.toDebugString());
		}

		Procedure procedure = procedureForTask(task.getTaskName());
		TaskRunner runner;
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

	private Procedure procedureForTask(String taskName) {
		if(procedureSupplierMap.containsKey(taskName)){
			return procedureSupplierMap.get(taskName).get();
		}
		else {
			throw new RuntimeException("Task \"" + taskName + "\" was not bound to a procedure supplier.");
		}
	}

	public void shutdown() {
		//workers.shutdown();
	}

	public boolean isExecutionOngoing(Execution exec) {
		if(executionFutureMap.containsKey(exec)) {
			return !executionFutureMap.get(exec).isEmpty();
		} else{
			return false;
		}
	}

	private interface TaskRunner extends   Runnable {
		Task getTask();
	}

	private static class ProcedureRunner implements  TaskRunner {
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

		@Override
		public Task getTask() {
			return task;
		}
	}


	private static class OnFailRunner implements  TaskRunner {
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

		@Override
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
