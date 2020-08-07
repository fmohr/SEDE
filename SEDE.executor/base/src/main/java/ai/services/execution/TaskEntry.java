package ai.services.execution;

/**
 * Tuple of AccessControlQueue, Execution and Task.
 */
public class TaskEntry {

    private final AccessControlQueue acq;

    private final Execution execution;

    private final Task task;

    public TaskEntry(AccessControlQueue acq, Execution execution, Task task) {
        this.acq = acq;
        this.execution = execution;
        this.task = task;
    }

    public AccessControlQueue getAcq() {
        return acq;
    }

    public Execution getExecution() {
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
