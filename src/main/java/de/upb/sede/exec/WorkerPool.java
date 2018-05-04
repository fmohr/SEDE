package de.upb.sede.exec;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerPool {
	private final ExecutorService workers;
	WorkerPool(int workerNumber){
		workers = Executors.newFixedThreadPool(workerNumber);
	}

	static class Worker extends Thread {
		private final long id;
		Worker() {
			id = Thread.currentThread().getId();
		}

		public long getId(){
			return id;
		}
	}

}
