package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainTaskFinisherOp implements TaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(MainTaskFinisherOp.class);

    @Override
    public boolean test(Task task) {
        if(task.isMainTaskPerformed() || task.isDependencyFailed()) {
            return true;
        } else {
            return false; // always matching
        }
    }

    @Override
    public TaskTransition apply(Task task) {
        if (task.isMainTaskPerformed()) {
            logger.trace("No more operator for completed task `{}`.", task);
            return TaskTransition.success();
        } else if(task.isDependencyFailed()){
            logger.trace("Task '{}' dependency failed.", task);
            return TaskTransition.error(new Exception("Dependency failed"));
        } else {
            logger.error("No operator matched task `{}`", task);
            return TaskTransition.error(new Exception("No operator carried out main task of: " + task));
        }
    }
}
