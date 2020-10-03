package ai.services.execution.operator;

import ai.services.execution.Task;
import ai.services.execution.TaskTransition;

/**
 * Implementations of this interface are applied to tasks to handle the workload.
 * There can be task type specific operators like an operator that uses reflection to invoke methods of a class to realise the instruction.
 * There can also be generic operators like logging that dont change the state of the task but are applied to tasks before and after their main task.
 *
 */
public interface TaskOperator  {

	boolean test(Task task);

    TaskTransition apply(Task task);

}

