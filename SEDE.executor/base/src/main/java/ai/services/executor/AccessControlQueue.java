package ai.services.executor;

import ai.services.execution.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This class synchronizes state mutating calls onto executions.
 * Workers can queue up for tasks.
 * When a state mutating operation is performed, like a new execution is started,
 * the workers are awoken to search for the newly available tasks.
 */
public class AccessControlQueue extends ExecutionRegistry {

    private static final Logger logger = LoggerFactory.getLogger(AccessControlQueue.class);

    public synchronized TaskEntry takeJob(WorkerProfile workerProfile) throws InterruptedException, QueueClosedException {
        removeIf(this::canBeRemoved);
        if(!iterate().hasNext() && isClosed) {
            throw new QueueClosedException();
        }
        Optional<TaskEntry> job = searchNextJob(workerProfile);
        while(!job.isPresent()) {
            logger.trace("No job found to take. Waiting until one becomes available.");
            this.wait();
            job = searchNextJob(workerProfile);
        }
        if(logger.isTraceEnabled())
            logger.trace("Job taken: {}", job.get());
        return job.get();
    }

    private Optional<TaskEntry> searchNextJob(WorkerProfile workerProfile) {
        Iterator<GraphTaskExecution> it = iterate();
        while(it.hasNext()) {
            GraphTaskExecution execution = it.next();
            Optional<Task> task = execution.takeNextWaitingTask(workerProfile);
            if(task.isPresent()) {
                TaskEntry job = new TaskEntry(this, execution, task.get());
                return Optional.of(job);
            }
        }
        return Optional.empty();
    }

    private boolean canBeRemoved(GraphTaskExecution execution) {
        return execution.isFinished() || execution.isInterrupted();
    }

    public synchronized boolean computeIfPresent(String execId, Consumer<GraphTaskExecution> execTask) {
        Optional<GraphTaskExecution> first = get(execId);
        if(first.isPresent()) {
            compute(first.get(), execTask);
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    public synchronized void compute(String execId, Consumer<GraphTaskExecution> execTask) {
        compute(createOrGet(execId), execTask);
    }

    public synchronized void compute(final GraphTaskExecution executor, Consumer<GraphTaskExecution> execTask) {
        this.compute(() -> execTask.accept(executor));
    }

    public synchronized void compute(Runnable runnable) {
        runnable.run();
        this.notifyAll();
    }

    public synchronized GraphTaskExecution waitUntil(String execId,
                                                     Predicate<Optional<GraphTaskExecution>> condition) throws InterruptedException {
        Optional<GraphTaskExecution> first = get(execId);
        while(!condition.test(first)) {
            this.wait();
            if(!first.isPresent()) {
                first = get(execId);
            }
        }
        return first.orElse(null);
    }

    public synchronized GraphTaskExecution waitUntilFinished(String execId) throws InterruptedException {
        return waitUntil(execId, exec -> exec.map(e -> e.isFinished() || e.isInterrupted()).orElse(true));
    }

}
