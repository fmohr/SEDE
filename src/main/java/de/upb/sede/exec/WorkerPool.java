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


	/**
	 * Use weak hashmap to have executions which are finished removed and avoid a memory leak.
	 */
	private final Map<String, Supplier<Procedure>> procedureSupplierMap = new HashMap<>();

	private final Supplier<Task> nextTaskSupplier;

	private final List<Thread> workerThreads;

	private final Map<Thread, Execution> workingOn = new ConcurrentHashMap<>();

	private boolean finished = false;


	WorkerPool(int workerNumber, Supplier<Task> nextTaskSupplier){
		this.nextTaskSupplier = nextTaskSupplier;
		workerThreads = new ArrayList<>(workerNumber);
		for (int i = 0; i < workerNumber; i++) {
			Thread t = new Thread(new Worker());
			workerThreads.add(t);
			t.start();
		}
	}


	public synchronized void interruptExec(Execution interruptedExeution) {
		logger.info("{} interrupted.", interruptedExeution.getExecutionId());
		/**
		 * cancel/interrupt every thread that is working on the given execution.
		 */
		for(Thread workingThread : workingOn.keySet()){
			if(workingOn.get(workingThread) == interruptedExeution){
				workingThread.interrupt();
			}
		}
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
		finished = true;
		for(Thread workers : workerThreads){
			workers.interrupt();
		}
	}

	private class Worker implements  Runnable {

		@Override
		public void run() {
			while(!finished){
				Task task = nextTaskSupplier.get();
				Procedure procedure = procedureForTask(task.getTaskName());
				ProcedureRunner  runner = new ProcedureRunner(task, procedure);
				workingOn.put(Thread.currentThread(), task.getExecution());
				runner.run();
				workingOn.remove(Thread.currentThread());
			}
		}
	}

	private static class ProcedureRunner implements  Runnable {
		private Task task;
		private Procedure procedure;
		ProcedureRunner(Task task, Procedure procedure) {
			this.task = task;
			this.procedure = procedure;
		}
		public void run() {
			logger.trace("{} started", task.toDebugString());
			task.setStarted();
			try{
				procedure.process(task);
			} catch(Exception ex) {
//				task.setError(ex); TODO
				task.setFailed();
				if(logger.isDebugEnabled()) {
					logger.error("ERROR during {}:\n", task, ex);
				}
			}
			finally {
				task.isDoneRunning();
			}
			logger.trace("{} finished", task.toDebugString());
		}
	}
}
