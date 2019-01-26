package de.upb.sede.webinterfaces.client;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AsyncClientRequest implements Future<Optional<String>>, Runnable {

	private boolean requestFailed = false;
	private boolean requestIsDone = false;
	private Optional<String> response = Optional.empty();
	private final BasicClientRequest request;
	private final String body;
	private Thread asynchonousThread = new Thread(this);

	public AsyncClientRequest(BasicClientRequest request, String body) {
		this.request = request;
		this.body = body;
		asynchonousThread.start();
	}

	private synchronized void setResponse(String response) {
		this.response = Optional.ofNullable(response);
	}

	private synchronized  Optional<String> getResponse() {
		return response;
	}


	public void run() {
		try (BasicClientRequest synchCall = request){
			setResponse(synchCall.send(body));
		} catch (Exception ex) {
			requestFailed = true;
		}
		requestIsDone = true;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public synchronized boolean isDone() {
		return requestIsDone;
	}

	public synchronized boolean hasFailed() {
		return requestFailed;
	}

	@Override
	public Optional<String> get() throws InterruptedException, ExecutionException {
		asynchonousThread.join();
		return getResponse();
	}

	@Override
	public Optional<String> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		asynchonousThread.join(unit.toMillis(timeout));
		return getResponse();
	}

	public static void joinAll(Collection<AsyncClientRequest> requests, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		long remainingMillis = unit.toMillis(timeout);
		long lastTimestamp = System.currentTimeMillis();
		for(AsyncClientRequest request : requests) {
			if(lastTimestamp < 0) {
				return;
			}
			request.get(remainingMillis, TimeUnit.MILLISECONDS);
			remainingMillis -= System.currentTimeMillis() - lastTimestamp;
			lastTimestamp = System.currentTimeMillis();
		}
	}


}
