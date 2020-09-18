package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.composition.graphs.nodes.BaseNode;

/**
 * Task operators that handle the main workload of a task.
 */
public abstract class MainTaskOperator extends AbstractOperator {

    private final boolean acceptFailedTasks;

    protected final Class<? extends BaseNode> taskDomain;

    public MainTaskOperator(boolean acceptFailedTasks, Class<? extends BaseNode> taskDomain) {
        this.taskDomain = taskDomain;
        this.acceptFailedTasks = acceptFailedTasks;
    }

    public MainTaskOperator(boolean acceptFailedTasks) {
        this.taskDomain = null;
        this.acceptFailedTasks = acceptFailedTasks;
    }


    public MainTaskOperator(Class<? extends BaseNode> taskDomain) {
        this(false, taskDomain);
    }

    public MainTaskOperator() {
        this(false, null);
    }

    protected TaskTransition mainTaskPerformed(Task t) {
        t.setMainTaskPerformed();
        return TaskTransition.pass();
    }

    public boolean test(Task task) {
        if(!acceptFailedTasks && task.isDependencyFailed()) {
            return false;
        }
        if(task.isFinished() || task.isMainTaskPerformed()) {
            return false;
        }
        if(task.isOfType(taskDomain)) {
            return detailedTest(task);
        }
        return false;
    }

    protected boolean detailedTest(Task task) {
        return true;
    }

}
