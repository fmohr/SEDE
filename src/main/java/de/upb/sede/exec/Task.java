package de.upb.sede.exec;

import de.upb.sede.util.Observable;
import de.upb.sede.util.Observer;

import java.util.*;

public final class Task implements Observer<Task>{

	private final Execution execution;

	private final String taskName;

	private final Map<String, Object> attributes;

	private final Set<Task> dependencies = new HashSet<>();

	/*
	 * Flags which define the state of the task.
	 */
	private boolean resolved = false, 		// resolve: true if the dependencies are all resolved. (This value is set by the notification method)
					started = false,  		// started: true if a worker has started processing this task.
					doneRunning = false,	// doneRunning: true if started and the worker has finished processing.
					failed = false,			// failed: true if done and an error has occured.
					succeeded = false;		// succeeded: true if done and the worker successfully carried out the task.


	private Observable<Task> taskState = Observable.ofInstance(this);



	public Task(Execution execution, String taskName, Map<String, Object> parameters) {
		this.execution = Objects.requireNonNull(execution);
		this.taskName = Objects.requireNonNull(taskName);
		this.attributes = Objects.requireNonNull(parameters);
	}

	public Execution getExecution() {
		return execution;
	}

	public String getTaskName() {
		return taskName;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}



	public Observable<Task> getState(){
		return taskState;
	}

	public void addDependency(Task task) {
		this.dependencies.add(task);
		task.getState().observe(this);
	}

	/**
	 * @param task  task with updated state
	 * @return true if the task is done and the dependency contains this task.
	 */
	@Override
	public boolean notifyCondition(Task task) {
		return task.resulted() && this.dependencies.contains(task);
	}

	/**
	 * Notification is invoked when a dependency task has resulted (failed or succeeded).
	 * @param task dependency task.
	 */
	@Override
	public void notification(Task task) {
		if(this.hasFailed()){
			/* this task may have failed already if another dependency of this task has failed. */
			return;
		}

		this.dependencies.remove(task);

		if(task.hasFailed()){
			failed();
		}
		else {
			updateDependendency();
		}
	}

	public void updateDependendency(){
		if(dependencies.isEmpty()){
			setResolved();
		}
	}

	@Override
	public boolean synchronizedNotification() {
		return true;
	}

	public boolean isResolved(){
		return resolved;
	}

	public boolean hasStarted() {
		return started;
	}

	public boolean isRunning() {
		return hasStarted() && !isDoneRunning();
	}

	public boolean isDoneRunning(){
		return doneRunning;
	}

	/**
	 * @return true, if the task has resulted in failure or success.
	 */
	public boolean resulted() {
		return hasFailed() || hasSucceeded();
	}

	public boolean hasFailed(){
		return failed;
	}

	public boolean hasSucceeded(){
		return succeeded;
	}

	public void setResolved(){
		resolved = true;
		taskState.update(this);
	}

	public void setStarted(){
		resolved = true;
		started = true;
		taskState.update(this);
	}

	public void setDone(){
		resolved = true;
		started = true;
		doneRunning = true;
		taskState.update(this);
	}

	public void succeeded(){
		resolved = true;
		started = true;
		doneRunning = true;
		succeeded = true;
		taskState.update(this);
	}

	public void failed(){
		resolved = true;
		started = true;
		doneRunning = true;
		failed = true;
		taskState.update(this);
	}


	public final boolean equals(Object otherObject) {
		return super.equals(otherObject);
	}

	public final int hashCode() {
		return super.hashCode();
	}
}
