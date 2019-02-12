package de.upb.sede.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorServices {
	private static final AtomicInteger poolNr = new AtomicInteger(0);

	/**
	 * Creates a new executor services which always run with a single daemon thread.
	 * The Thread name is Pool_{pool nr}/Single-Daemon-Thread_{thread nr}.
	 *
	 * @return New Executor Service that executes with a single daemon thread.
	 */
	public static ExecutorService createSingleDaemonThreaded() {
		final AtomicInteger threadNr = new AtomicInteger(0);
		return Executors.newSingleThreadExecutor(r -> {
			Thread thread = new Thread();
			thread.setDaemon(true);
			thread.setPriority(6);
			String threadName =
					new StringBuilder("Pool_")
							.append(poolNr.incrementAndGet())
							.append("/Single-Daemon-Thread_")
							.append(threadNr.incrementAndGet())
							.toString();
			thread.setName(threadName);
			return thread;
		});
	}
}
