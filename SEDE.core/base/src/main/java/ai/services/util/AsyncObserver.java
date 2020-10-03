package ai.services.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * This utility class warps an observer and executes updates on the given executor.
 * If no executor is given a single daemon threaded one will be created.
 * @param <T> The message type of the observer.
 */
public class AsyncObserver<T>  implements Observer<T>{

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
		NotificationTask<T> task = new NotificationTask<T>(synchronizedObserver, t);
		Future<?> future = asyncExecutor.submit(task);
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
