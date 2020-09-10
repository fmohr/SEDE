package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;

import java.util.Collection;
import java.util.Optional;

public class OpCollection implements TaskOperator {


    private Collection<TaskOperator> operators;

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
            return TaskTransition.error(new RuntimeException("No matching operator found for task: " + task));
        }
        return matchingOp.get().apply(task);
    }




}
