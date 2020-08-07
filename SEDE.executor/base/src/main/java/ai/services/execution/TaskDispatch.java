package ai.services.execution;

import ai.services.execution.local.GraphOperator;
import ai.services.execution.operator.TaskOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Workers carry a JobDispatch Supplier around.
 * JobDispatch instances contains the usual routine to start handling a task.
 * For each time a task is taken to be handled one JobDispatch instance is created.
 * Threads running on the same host can use LocalJobDispatch.
 */
public abstract class TaskDispatch {

    private static final Logger logger = LoggerFactory.getLogger(TaskDispatch.class);

    private final boolean enqueueRunningTasks = true;

    private final AccessControlQueue acq;

    private Execution execution;

    private Task task;

    private AtomicBoolean isInterrupted = new AtomicBoolean(false);

    private AtomicBoolean isRunning = new AtomicBoolean(false);

    protected TaskDispatch(AccessControlQueue acq) {
        this.acq = acq;
    }

    protected Execution getExecution() {
        return Objects.requireNonNull(execution);
    }

    protected Task getTask() {
        return Objects.requireNonNull(task);
    }

    public final void fetchJobAndExecute() throws InterruptedException, QueueClosedException {
        fetchJob();
        executeJob();
    }

    protected void fetchJob() throws InterruptedException, QueueClosedException {
        TaskEntry job = acq.takeJob(getWorkerProfile());
        execution = Objects.requireNonNull(job.getExecution());
        task = Objects.requireNonNull(job.getTask());
    }

    protected void executeJob() {
        TaskOperator taskOperator = getTaskOperator();

        logger.debug("Worker started to work at: Task `{}`", getTask());

        acq.compute(getExecution(), exec -> {
            setRunning();
            exec.registerJobDispatch(this);
        });

        // Task operator will now try to finish the task:
        isRunning.set(true);
        TaskTransition transitionTmp;
        try {
            transitionTmp = taskOperator.apply(task);
        } catch(Throwable t) {
            logger.error("BUG: Task operator threw an unexpected error: {}\n", taskOperator.getClass().getName(), t);
            Exception error;
            if(t instanceof Exception) {
                error = (Exception) t;
            } else {
                error = new Exception(t);
            }
            transitionTmp = TaskTransition.error(error);
        }
        final TaskTransition transitionFinal = transitionTmp;
        isRunning.set(false);

        AtomicBoolean toContinue = new AtomicBoolean(false);
        acq.compute(getExecution(), ex -> {
            ex.deregisterJobDispatch(this);
            if(isInterrupted.get()) {
                task.setError(new InterruptedException());
                task.set(Task.State.FAILURE);
            }
            handleTaskTransition(transitionFinal, toContinue);
        });
        if(toContinue.get()) {
            executeJob();
        }
    }

    protected void setRunning() {
        assert task.is(Task.State.QUEUED) ||  task.is(Task.State.RUNNING): "Task `" + getTask() + "` is " + task.getCurrentState() + " but should be queued.";
        getTask().set(Task.State.RUNNING);
    }

    protected void handleTaskTransition(TaskTransition transition,
                                        AtomicBoolean toContinue) {
        transition.performTransition(task);
        if(task.is(Task.State.RUNNING)) {
            logger.debug("Task `{}` is still running after applying operator. " +
                    "Putting the task back into the queue.", task);
            if(enqueueRunningTasks)
                task.set(Task.State.QUEUED);
            else
                toContinue.set(true);
        }
        if(task.is(Task.State.QUEUED)) {
            getExecution().enqueueTask(task);
        }
        if(task.is(Task.State.WAITING)) {
            getExecution().parkTask(task);
        }
        if(task.isFinished()) {
            for (GraphOperator graphOp : task.getGraphOps()) {
                graphOp.perform(getExecution(), task);
            }
        }
    }

    public abstract WorkerProfile getWorkerProfile();

    public abstract TaskOperator getTaskOperator();

    public abstract void handleInterrupt();

    public synchronized void interrupt() {
        isInterrupted.set(true);
        if(isRunning.get()) {
            handleInterrupt();
        }
    }

}
