package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

public class OpCollection implements TaskOperator {

    private static final Logger logger = LoggerFactory.getLogger(OpCollection.class);

    private final Collection<TaskOperator> operators;

    public OpCollection(Collection<TaskOperator> operators) {
        this.operators = operators;
    }

    @Override
    public boolean test(Task task) {
        return operators.stream().anyMatch(op -> op.test(task));
    }

    @Override
    public TaskTransition apply(Task task) {
        Optional<TaskOperator> matchingOp = operators.stream().filter(op -> op.test(task)).findFirst();
        if(!matchingOp.isPresent()) {
            logger.error("No matching operator found for task: {}", task);
            return TaskTransition.error(new RuntimeException("No matching operator found for task: " + task));
        }
        return matchingOp.get().apply(task);
    }




}
