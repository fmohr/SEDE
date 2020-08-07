package ai.services.execution.local;

import ai.services.execution.AccessControlQueue;
import ai.services.execution.operator.TaskOperator;
import ai.services.execution.TaskDispatch;
import ai.services.execution.WorkerProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the java thread task dispatch.
 * It simply captures
 */
public class LocalTaskDispatch extends TaskDispatch {

    private static final Logger logger = LoggerFactory.getLogger(LocalTaskDispatch.class);

    private final TaskOperator taskOperator;

    private final Thread worker;

    LocalTaskDispatch(AccessControlQueue acq, TaskOperator taskOperator, Thread worker) {
        super(acq);
        this.taskOperator = taskOperator;
        this.worker = worker;
    }

    @Override
    public WorkerProfile getWorkerProfile() {
        return WorkerProfile.SUPER_STAR;
    }

    @Override
    public TaskOperator getTaskOperator() {
        return taskOperator;
    }

    @Override
    public void handleInterrupt() {
        logger.info("Local worker (thread: {}) has been interrupted while performing task: {}",
                worker.getName(), getTask());
        worker.interrupt();
    }

}
