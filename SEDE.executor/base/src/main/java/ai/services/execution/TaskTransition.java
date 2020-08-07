package ai.services.execution;

import de.upb.sede.core.SEDEObject;

/**
 * Instances of this task realise the state transition of a task.
 * TaskTransition instances are returned by operators that are applied to task instances.
 * The transition instances are then invoked in a thread safe scope.
 * So it is guaranteed that no two task transition for one execution are performed at the same time.
 *
 */
public interface TaskTransition {

    static TaskTransition fieldAssignment(String fieldName, SEDEObject newField) {
        return task -> task.getFieldContext().setFieldValue(fieldName, newField);
    }

    public void performTransition(Task task);

    static TaskTransition success() {
        return task -> task.set(Task.State.SUCCESS);
    }

    static TaskTransition mainTaskPerformed() {
        return Task::setMainTaskPerformed;
    }

    static TaskTransition error(Exception ex) {
        return task -> {
            task.setError(ex);
            task.set(Task.State.FAILURE);
        };
    }

    static TaskTransition waitForField(String fieldname) {
        return task -> {
            task.addWaitingCondition(t -> !t.getFieldContext().hasField(fieldname));
            task.set(Task.State.WAITING);
        };
    }

    static TaskTransition pass() {
        return task -> { };
    }

}
