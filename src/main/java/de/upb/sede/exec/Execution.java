package de.upb.sede.exec;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.util.Observable;
import de.upb.sede.util.Observer;
import de.upb.sede.webinterfaces.client.BasicClientRequest;

/**
 * Represents one execution.
 */
public abstract class Execution {

	private final ExecutionEnvironment environment;

	private final String execId;

	private final Observable<Execution> state;

	/**
	 * Flag that indicates that the execution has been interrupted.
	 */
	private boolean interrupted = false;

	/**
	 * Tasks that are resolved but haven't started processing yet.
	 */
	private final Set<Task> waitingTasks = new HashSet<>();

	/**
	 * Set of tasks that are waiting for an event to happen. E.g. waiting for input data.
	 */
	private final Set<Task> unfinishedTasks = new HashSet<>();

	/**
	 * Observes all tasks.
	 * Once an observed task changes its state to resolved, this observer will put it into the waiting-Tasks set.
	 */
	private final Observer<Task> unresolvedTasksObserver = Observer.lambda(
			Task::isWaiting, this::taskResolved);

	/**
	 * Observes all tasks.
	 * Once an observed task has started processing, it will be removed from the waiting-Tasks set.
	 */
	private final Observer<Task> waitingTasksObserver = Observer.lambda(
			Task::hasStarted, this::taskStarted);

	/**
	 * Observes all tasks.
	 * Once an observed tasks has finished (success or fail), it will be removed from the unfinished-Tasks set.
	 */
	private final Observer<Task> unfinishedTasksObserver = Observer.lambda(
			Task::hasFinished, this::taskFinished);

	/**
	 * An array of any observer of this class that needs to observe every task added to this execution object.
	 * See: addTask method.
	 */
	@SuppressWarnings("unchecked")
	private final Observer<Task> observersOfAllTasks[] = (Observer<Task>[]) new Object[]{
			waitingTasksObserver,
			unresolvedTasksObserver,
			unfinishedTasksObserver};

	public Execution(String execId) {
		Objects.requireNonNull(execId);
		this.execId = execId;
		this.environment = new ExecutionInv();
		this.state = Observable.ofInstance(this);
	}

	/**
	 * Create a new client request based on the request info.
	 * This method does not affect the state of the execution object.
	 *
	 * @return a new client request.
	 */
	public abstract BasicClientRequest createClientRequest(Object requestInfo);

	/**
	 * Create new new service instance handle with the given service instance.
	 * This method does not affect the state of the execution object.
	 *
	 * @return a new ServiceInstanceHandle
	 */
	public abstract ServiceInstanceHandle createServiceInstanceHandle(Object serviceInstance);

	/**
	 * Returns the execution-id of this execution.
	 *
	 * @return the execution-id
	 */
	public String getExecutionId() {
		return execId;
	}

	/**
	 * Returns an observable instance of the execution state.
	 * This state updates whenever a task finishes.
	 *
	 * @return observable of the execution.
	 */
	public Observable<Execution> getState() {
		return state;
	}

	/**
	 * Returns the environment of this execution.
	 *
	 * @return the exeuction environment.
	 */
	public ExecutionEnvironment getExecutionEnvironment() {
		return environment;
	}


	/*
	 * private methods to allow synchronized access to the 2 set of tasks.
	 * Used by the observers.
	 */

	private final synchronized void taskResolved(Task task) {
		waitingTasks.add(task);
	}

	private final synchronized void taskStarted(Task task) {
		waitingTasks.remove(task);
	}

	private final synchronized void taskFinished(Task task) {
		unfinishedTasks.remove(task);
	}

	/**
	 * Adds the given task to this execution. <p>
	 * If it wasn't added before, it will be added to the set of unfinished tasks and waiting tasks.  <p>
	 * Additionally every observer in <tt>observersOfAllTasks</tt> will observe the given task.
	 *
	 * @param task assigned to this execution.
	 */
	synchronized void addTask(Task task) {
		if (task.getExecution() != this) {
			throw new RuntimeException("Bug: Task states that it belongs to another execution.");
		}
		if (task.hasStarted()) {
			throw new RuntimeException("Bug: the given task was already started before.");
		}
		boolean newTask = unfinishedTasks.add(task);
		if (newTask) {
			waitingTasks.add(task);
			for (Observer<Task> taskObserver : observersOfAllTasks) {
				task.getState().observe(taskObserver);
			}
		}
	}

	/**
	 * Returns true if the set of all unfinished tasks is empty or the execution was interrupted.
	 * IF true, the set returned by getWaitingTasks is empty too.
	 *
	 * @return true if the execution has finished
	 */
	synchronized boolean hasExecutionFinished() {
		return interrupted || unfinishedTasks.isEmpty();
	}

	/**
	 * Returns the set of tasks that are waiting to be executed at the time the query is made.
	 *
	 * @return set of waiting tasks
	 */
	synchronized Set<Task> getWaitingTasks() {
		if (hasExecutionFinished()) {
			return Collections.EMPTY_SET;
		} else {
			return Collections.unmodifiableSet(waitingTasks);
		}
	}

	static class ExecutionInv extends ConcurrentHashMap<String, SEDEObject> implements ExecutionEnvironment {

	}

}