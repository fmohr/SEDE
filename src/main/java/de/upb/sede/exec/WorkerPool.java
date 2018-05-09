package de.upb.sede.exec;

import de.upb.sede.procedure.Procedure;
import de.upb.sede.util.Observer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WorkerPool {

	private final ExecutorService workers;

	/**
	 * Use weak hashmap to have executions which are finished removed and avoid a memory leak.
	 */
	private final Map<Execution, List<Future>> executionFutureMap = new WeakHashMap<>();


	WorkerPool(int workerNumber){
		workers = Executors.newFixedThreadPool(workerNumber);
	}


	public synchronized void processTask(Task task){
		Procedure procedure = Procedure.procedureForTask(task);
		Future future = workers.submit(procedure);
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
		}
	}

	private synchronized  void removeExec(Execution execution) {
		executionFutureMap.remove(execution);
	}
}
