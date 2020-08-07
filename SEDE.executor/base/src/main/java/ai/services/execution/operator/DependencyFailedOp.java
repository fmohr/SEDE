package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;

public class DependencyFailedOp implements TaskOperator {
    @Override
    public boolean test(Task task) {
        return task.isDependencyFailed() && task.isMainTaskPerformed();
    }

    @Override
    public TaskTransition apply(Task task) {
        return TaskTransition.error(new RuntimeException("Dependency failed"));
    }
}
