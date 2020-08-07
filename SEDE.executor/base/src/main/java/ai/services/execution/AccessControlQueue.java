package ai.services.execution;

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
            this.wait();
            logger.debug("No job found to take. Waiting until one becomes available.");
            job = searchNextJob(workerProfile);
        }
        if(logger.isTraceEnabled())
            logger.trace("Job taken: {}", job.get());
        return job.get();
    }

    private Optional<TaskEntry> searchNextJob(WorkerProfile workerProfile) {
        Iterator<Execution> it = iterate();
        while(it.hasNext()) {
            Execution execution = it.next();
            Optional<Task> task = execution.takeNextWaitingTask(workerProfile);
            if(task.isPresent()) {
                TaskEntry job = new TaskEntry(this, execution, task.get());
                return Optional.of(job);
            }
        }
        return Optional.empty();
    }

    private boolean canBeRemoved(Execution execution) {
        if(execution.isFinished()) {
            return true;
        }
        return execution.isInterrupted();
    }

    public synchronized boolean computeIfPresent(String execId, Consumer<Execution> execTask) {
        Optional<Execution> first = get(execId);
        if(first.isPresent()) {
            compute(first.get(), execTask);
            return true;
        } else {
            return false;
        }
    }

    public synchronized void compute(String execId, Consumer<Execution> execTask) {
        compute(createOrGet(execId), execTask);
    }

    public synchronized void compute(final Execution executor, Consumer<Execution> execTask) {
        this.compute(() -> execTask.accept(executor));
    }

    public synchronized void compute(Runnable runnable) {
        runnable.run();
        this.notifyAll();
    }

    public synchronized Execution waitUntil(String execId,
                                            Predicate<Optional<Execution>> condition) throws InterruptedException {
        Optional<Execution> first = get(execId);
        while(!condition.test(first)) {
            this.wait();
            if(!first.isPresent()) {
                first = get(execId);
            }
        }
        return first.orElse(null);
    }

    public synchronized void waitUntilFinished(String execId) throws InterruptedException {
        waitUntil(execId, exec -> exec.map(e -> e.isFinished() || e.isInterrupted()).orElse(true));
    }


}
