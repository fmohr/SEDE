package de.upb.sede.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This utility class warps an observer and executes updates on the given executor.
 * If no executor is given a single daemon threaded one will be created.
 * @param <T> The message type of the observer.
 */
public class AsyncObserver<T>  implements Observer<T>{


    private final static Logger logger = LoggerFactory.getLogger(AsyncObserver.class);

	private final Observer<T> synchronizedObserver;
	private final ExecutorService asyncExecutor;


	public AsyncObserver(Observer<T> synchronizedObserver, ExecutorService asyncExecutor) {
		this.synchronizedObserver = synchronizedObserver;
		this.asyncExecutor = asyncExecutor;
	}

    public ExecutorService getAsyncExecutor() {
        return asyncExecutor;
    }

    public AsyncObserver(Observer<T> synchronizedObserver) {
		this.synchronizedObserver = synchronizedObserver;
		this.asyncExecutor = ExecutorServices.createSingleDaemonThreaded();
	}

	public boolean notifyCondition(T t) {
		return synchronizedObserver.notifyCondition(t);
	}

	public void notification(T t) {
		if(asyncExecutor.isShutdown()) {
		    logger.warn("Execution has been shut down, but notification {} was still submitted. Executing it directly.", t);
            synchronizedObserver.notification(t);
        } else {
            NotificationTask<T> task = new NotificationTask<T>(synchronizedObserver, t);
            asyncExecutor.submit(task);
        }
	}

	public boolean removeAfterNotification(T t) {
		return synchronizedObserver.removeAfterNotification(t);
	}

	/**
	 * No need to synchronize from outside as the notification process is handled asynchronously.
	 * @return False
	 */
	public boolean synchronizedNotification() {
		return false;
	}

	private static class NotificationTask<T> implements Runnable {

		private final Observer<T> toBeNotified;
		private final T message;

		private NotificationTask(Observer<T> toBeNotified, T message) {
			this.toBeNotified = toBeNotified;
			this.message = message;
		}

		@Override
		public void run() {
			toBeNotified.notification(message);
		}
	}
}
