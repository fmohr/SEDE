package de.upb.sede.exec;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import de.upb.sede.util.*;
import de.upb.sede.util.Observable;
import de.upb.sede.util.Observer;
import org.slf4j.Logger;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.interfaces.IExecution;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Represents one execution.
 */
public class Execution implements IExecution {

	private static final Logger logger = getLogger(Execution.class);

	private final ExecutionEnv environment;

	private final String execId;

	private final Observable<Execution> state;

	private final Observable<Task> runnableTasks = new Observable<Task>();

	private final ExecutorConfiguration executorConfiguration;


	/*
	 * This executor service handle observer updates for all observers in this execution.
	 * Procedures and tasks all use this messenger to create asynchronous observers.
	 * Do not use this service for tasks outside of this execution and don't use it for heavy duty tasks.
	 */
	private final ExecutorService messenger = ExecutorServices.createSingleDaemonThreaded();

	/**
	 * Flag that indicates that the execution has been interrupted.
	 */
	private boolean interrupted = false;

	private boolean started = false;

	/**
	 * Tasks that are resolved but haven't started processing yet.
	 */
	private final Set<Task> waitingTasks = new HashSet<>();

	/**
	 * Set of tasks that are not finished yet. Every task is added to this set at the beginning when the execuiton is being deserialized.
	 * Each time a task finished it will be removed.
	 */
	private final Set<Task> unfinishedTasks = new HashSet<>();


	/**
	 * Set of all tasks of this execution. Tasks are added to this set at the beginning of the execution. The set is then left untouched.
	 */
	private final Set<Task> allTasks = new HashSet<>();


	/**
	 * Observes all tasks. Once an observed task changes its state to resolved, this
	 * observer will put it into the waiting-Tasks set.
	 */
	private final Observer<Task> unresolvedTasksObserver = new AsyncObserver<>(
			Observer.lambda(Task::isWaiting, this::taskResolved), getMessenger());


	/**
	 * Observes all tasks. Once an observed task has started processing, it will be
	 * removed from the waiting-Tasks set.
	 */
	private final Observer<Task> waitingTasksObserver = new AsyncObserver<>(
			Observer.lambda(Task::hasStarted, this::taskStarted), getMessenger());

	/**
	 * Observes all tasks. Once an observed tasks has finished (success or fail), it
	 * will be removed from the unfinished-Tasks set.
	 */
	private final Observer<Task> unfinishedTasksObserver = new AsyncObserver<>(
			Observer.lambda(Task::hasFinished, this::taskFinished), getMessenger());


	/**
	 * An array of any observer of this class that needs to observe every task added
	 * to this execution object. See: addTask method.
	 */
	private final List<Observer<Task>> observersOfAllTasks = Arrays.asList(waitingTasksObserver,
			unresolvedTasksObserver, unfinishedTasksObserver);
	/**
	 * Default constructor.
	 * 
	 * @param execId
	 *            identifier of this execution.
	 */

	public Execution(String execId, ExecutorConfiguration executorConfiguration) {
		Objects.requireNonNull(execId);
		logger.info("Execution {} was created.", execId);
		this.execId = execId;
		this.environment = new ExecutionEnv();
		this.state = Observable.ofInstance(this);
		this.executorConfiguration = executorConfiguration;
	}

	/**
	 * Returns the execution-id of this execution.
	 *
	 * @return the execution-id
	 */
	public String getExecutionId() {
		return execId;
	}

	/**
	 * Returns an observable instance of the execution state. This state updates
	 * whenever a task finishes.
	 *
	 * @return observable of the execution.
	 */
	public Observable<Execution> getState() {
		return state;
	}

	/**
	 * Returns the environment of this execution.
	 *
	 * @return the execution environment.
	 */
	public ExecutionEnvironment getEnvironment() {
		return environment;
	}

	/*
	 * private methods to allow synchronized access to the 2 set of tasks. Used by
	 * the observers and are called therefore automatically.
	 */

	private final void taskResolved(Task task) {
		performLater(() ->  {
			/* notify observers about this new resolved task. */
			runnableTasks.update(task);
			state.update(this);
		});
	}

	private final void taskStarted(Task task) {
		performLater(() -> {
			waitingTasks.remove(task);
			state.update(this);
		});
	}

	private final void taskFinished(Task task) {
		performLater(() -> {
			unfinishedTasks.remove(task);
			state.update(this);
		});
	}

	/**
	 * Adds the given task to this execution.
	 * <p>
	 * If it wasn't added before, it will be added to the set of unfinished tasks
	 * and waiting tasks.
	 * <p>
	 * Additionally every observer in <tt>observersOfAllTasks</tt> will observe the
	 * given task.
	 *
	 * @param task
	 *            assigned to this execution.
	 */
	synchronized void addTask(Task task) {
		performLater( () ->  {
			if (task.getExecution() != this) {
				throw new RuntimeException("Bug: Task states that it belongs to another execution.");
			}
			if (task.hasStarted()) {
				throw new RuntimeException("Bug: the given task was already started before.");
			}
			boolean newTask = unfinishedTasks.add(task);
			if (newTask) {
				waitingTasks.add(task);
				allTasks.add(task);
				for (Observer<Task> taskObserver : observersOfAllTasks) {
					task.getState().observe(taskObserver);
				}
			}
		});
	}

	/**
	 * Returns true if the set of all unfinished tasks is empty or the execution was
	 * interrupted. IF true, the set returned by getWaitingTasks is empty too.
	 *
	 * @return true if the execution has finished
	 */
	public boolean hasExecutionFinished() {
		return interrupted || unfinishedTasks.isEmpty();
	}

	/**
	 * Returns true if the execution was interrupted. IF true, the set returned by
	 * getWaitingTasks is empty too.
	 *
	 * @return true if the execution was interrupted
	 */
	public synchronized boolean hasExecutionBeenInterrupted() {
		return interrupted;
	}

	/**
	 * Returns true if the set of all unfinished tasks is empty. IF true, the set
	 * returned by getWaitingTasks is empty too.
	 *
	 * @return true all tasks of this execution is done.
	 */
	public synchronized boolean zeroTasksRemaining() {
		return unfinishedTasks.isEmpty();
	}

	/**
	 * Returns the set of tasks that are waiting to be executed at the time the
	 * query is made.
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

	/**
	 * Returns the set of tasks that are unfinished.
	 *
	 * @return set of unfinished tasks
	 */
	public synchronized Set<Task> getUnfinishedTasks() {
		if (hasExecutionFinished()) {
			return Collections.EMPTY_SET;
		} else {
			return Collections.unmodifiableSet(unfinishedTasks);
		}
	}

	/**
	 * Returns the set of all tasks of this execution.
	 * @return set of all tasks.
	 */
	public Set<Task> getAllTasks() {
		return Collections.unmodifiableSet(allTasks);
	}

	/**
	 * Returns the amount of tasks that are unfinished.
	 *
	 * @return amount of unfinished tasks
	 */
	int getUnfinishedTasksCount() {
		if (hasExecutionFinished()) {
			return 0;
		} else {
			return unfinishedTasks.size();
		}
	}

	Observable<Task> getRunnableTasksObservable() {
		return runnableTasks;
	}

	void interrupt() {
		logger.info("Execution {} has been interrupted.", getExecutionId());
		performLater(() -> {
			interrupted = true;
			state.update(this);
		});
	}

	public ExecutorConfiguration getConfiguration() {
		return executorConfiguration;
	}

	public void start() {
		performLater(() -> {
			logger.info("Execution {} has been started.", getExecutionId());
			started = true;
			state.update(this);
		});
	}

	public boolean hasStarted() {
		return started;
	}

	public void forEachTask(Consumer<Task> taskConsumer) {
		for(Task task : allTasks) {
			taskConsumer.accept(task);
		}
	}

	/**
	 * Enqueues the given runnable to be performed later.
	 * Use this to perform changes onto this execution.
	 *
	 * @param runnable task to be done.
	 * @return empty future. Performing Future::get on the returned object will block until the task is completed.
	 */
	public Future<?> performLater(Runnable runnable) {
		return getMessenger().submit(runnable);
	}

	/**
	 * Enqueues the given callabe to be performed later.
	 * Use this to perform changes onto this execution.
	 *
	 * @param callable task to be done.
	 * @return Performing Future::get on the returned object will block until the task is completed and returns the returned object of the callable.
	 */
	public <T> Future<T> performLater(Callable<T> callable) {
		return getMessenger().submit(callable);
	}


	/**
	 * This executor service handle observer updates for all observers in this execution.
	 * Procedures and tasks all use this messenger to create asynchronous observers.
	 * Do not use this service for tasks outside of this execution and don't use it for heavy duty tasks.
	 * @return Executor service used to communicate messages inside of an execution.
	 */
	public ExecutorService getMessenger() {
		return messenger;
	}


}