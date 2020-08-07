package ai.services.execution.operator;

import ai.services.execution.Execution;
import ai.services.execution.Task;
import ai.services.execution.local.GraphOperator;

import java.util.Set;

/**
 * Contains a task and all its dependencies.
 * When perfoming this operator, it will mark the task as ready iff all its dependencies are successfull.
 *
 * Instances of this are created for each task with dependencies and added to all the dependencies as graph operators.
 */
public class GraphDependencyOperator implements GraphOperator {

    private Task task;

    private Set<Task> dependencies;

    public GraphDependencyOperator(Task task, Set<Task> dependencies) {
        this.task = task;
        this.dependencies = dependencies;
    }

    @Override
    public synchronized void perform(Execution ex, Task finishedTask) {
        if(!finishedTask.isFinished()) {
            throw new IllegalArgumentException(String.format("Task was not finished before calling its graph dependency operation: %s", finishedTask));
        }
        if(dependencies.isEmpty()) {
            return;
        }
        if(finishedTask.is(Task.State.FAILURE)) {
            dependencies.clear();
            task.setDependencyFailed();
        } else {
            dependencies.remove(finishedTask);
        }
        if (dependencies.isEmpty()) {
            assert task.is(Task.State.DISABLED);
            task.set(Task.State.QUEUED);
            ex.enqueueTask(task);
        }
    }
}
