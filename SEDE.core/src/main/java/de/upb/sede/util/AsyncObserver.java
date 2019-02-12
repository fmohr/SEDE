package de.upb.sede.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This utility class warps an observer and executes updates on the given executor.
 * If no executor is given a single daemon threaded one will be created.
 * @param <T> The message type of the observer.
 */
public class AsyncObserver<T>  implements Observer<T>{

	private final Observer<T> synchronizedObserver;
	private final ExecutorService asyncExecutor;
	private AtomicBoolean hasBeenNotified = new AtomicBoolean(false);


	public AsyncObserver(Observer<T> synchronizedObserver, ExecutorService asyncExecutor) {
		this.synchronizedObserver = synchronizedObserver;
		this.asyncExecutor = asyncExecutor;
	}



	public AsyncObserver(Observer<T> synchronizedObserver) {
		this.synchronizedObserver = synchronizedObserver;
		this.asyncExecutor = ExecutorServices.createSingleDaemonThreaded();
	}

	public boolean notifyCondition(T t) {
		if(! hasBeenNotified.get()) {
			/*
			 * First time notification:
			 */
			return synchronizedObserver.notifyCondition(t);
		} else {
			/*
			 * Second time notification:
			 * Note that in the asynchronous setting the removeAfterNotification check is performed before the actual notification.
			 * Thus observer who need to be removed after their first notification, assume there won't be a second notification.
			 * This construct guards them from being notified mutliple times:
			 */
			return synchronizedObserver.notifyCondition(t) && ! synchronizedObserver.removeAfterNotification(t);
		}
	}

	public void notification(T t) {
		hasBeenNotified.set(true);
		NotificationTask<T> task = new NotificationTask<T>(synchronizedObserver, t);
		asyncExecutor.submit(task);
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
