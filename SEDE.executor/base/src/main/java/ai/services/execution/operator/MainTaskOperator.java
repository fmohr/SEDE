package ai.services.execution.operator;

import ai.services.execution.Task;
import de.upb.sede.composition.graphs.nodes.BaseNode;

/**
 * Task operators that handle the main workload of a task.
 */
public abstract class MainTaskOperator extends AbstractOperator {

    private final Class<? extends BaseNode> taskDomain;

    public MainTaskOperator(Class<? extends BaseNode> taskDomain) {
        this.taskDomain = taskDomain;
    }

    public MainTaskOperator() {
        this.taskDomain = null;
    }

    public boolean test(Task task) {
        if(task.isDependencyFailed() || task.isFinished() || task.isMainTaskPerformed()) {
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
