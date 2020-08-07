package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import ai.services.execution.operator.AbstractOperator;

/**
 * Task operators that handle the main workload of a task.
 */
public abstract class MainTaskOperator extends AbstractOperator {

    private final Class<?> domainNode;

    public MainTaskOperator(Class<?> domainNode) {
        this.domainNode = domainNode;
    }

    public MainTaskOperator() {
        this.domainNode = null;
    }

    public boolean test(Task task) {
        if(task.isDependencyFailed() || task.isFinished() || task.isMainTaskPerformed()) {
            return false;
        }
        if(domainNode == null || domainNode.isInstance(task.getNode())) {
            return operationMatches(task);
        }
        return false;
    }

    protected boolean operationMatches(Task task) {
        return true;
    }

}
