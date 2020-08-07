package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOperator implements TaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(AbstractOperator.class);

    @Override
    public TaskTransition apply(Task task) {
        try {
            return runTask(task);
        } catch (Exception exception) {
            logger.error("Error during execution of task {}, performer of type: {}",
                task.getNode().getText(), this.getClass().getSimpleName(), exception);
            return TaskTransition.error(exception);
        } catch(Throwable thr) {
            logger.error("Unexpected throwable while executing task {}, performer of type: {}. Masking it in a RuntimeException",
                task.getNode().getText(), this.getClass().getSimpleName(), thr);
            return TaskTransition.error(new Exception(thr.getMessage(), thr));
        }
    }

    abstract TaskTransition runTask(Task t) throws Exception;

}
