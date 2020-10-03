package ai.services.execution;

import ai.services.executor.AccessControlQueue;

/**
 * Tuple of AccessControlQueue, Execution and Task.
 */
public class TaskEntry {

    private final AccessControlQueue acq;

    private final GraphTaskExecution execution;

    private final Task task;

    public TaskEntry(AccessControlQueue acq, GraphTaskExecution execution, Task task) {
        this.acq = acq;
        this.execution = execution;
        this.task = task;
    }

    public AccessControlQueue getAcq() {
        return acq;
    }

    public GraphTaskExecution getExecution() {
        return execution;
    }

    public Task getTask() {
        return task;
    }

    @Override
    public String toString() {
        return String.format("Job(Ex=%s, Task=%s)", execution.getExecutionId(), task.toString());
    }
}
