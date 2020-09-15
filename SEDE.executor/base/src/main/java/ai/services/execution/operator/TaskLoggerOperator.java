package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskLoggerOperator implements TaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(TaskLoggerOperator.class);

    private final TaskOperator delegate;

    public TaskLoggerOperator(TaskOperator delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean test(Task task) {
        boolean delegateTest =  delegate.test(task);
        if(delegateTest || logger.isTraceEnabled()) {
            logger.debug("Task-operator '{}' tested '{}' to task '{}'",
                getDelegateClassName(), delegateTest ? "positive": "negative", task);
        }
        return delegateTest;
    }

    @Override
    public TaskTransition apply(Task task) {
        if(logger.isDebugEnabled())
            logger.debug("Applying task-operator '{}' to task '{}'.", getDelegateClassName(), task);
        TaskTransition taskTransition = delegate.apply(task);
        if(logger.isDebugEnabled())
            logger.debug("Finished applying task-operator '{}' to task '{}'.\nReturned transition: {}", getDelegateClassName(), task, taskTransition);
        return taskTransition;
    }

    private String getDelegateClassName() {
        return delegate.getClass().getSimpleName();
    }
}
