package ai.services.execution;

import ai.services.composition.graphs.nodes.INotification;
import ai.services.core.SEDEObject;

/**
 * Instances of this task realise the state transition of a task.
 * TaskTransition instances are returned by operators that are applied to task instances.
 * The transition instances are then invoked in a thread safe scope.
 * So it is guaranteed that no two task transition for one execution are performed at the same time.
 *
 */
public interface TaskTransition {

    void performTransition(Task task);


    default TaskTransition then(TaskTransition other) {
        return new TaskTransition() {
            @Override
            public void performTransition(Task task) {
                TaskTransition.this.performTransition(task);
                other.performTransition(task);
            }

            @Override
            public String toString() {
                return this.toString() + " then " + other.toString();
            }
        };
    }

    static TaskTransition fieldAssignment(String fieldName, SEDEObject newField) {
        return new TaskTransition() {
            @Override
            public void performTransition(Task task) {
                task.getFieldContext().setFieldValue(fieldName, newField);
            }

            @Override
            public String toString() {
                return "Assign field: " + fieldName;
            }
        };
    }

    static TaskTransition success() {
        return new TaskTransition() {
            @Override
            public void performTransition(Task task) {
                task.set(Task.State.SUCCESS);
            }

            @Override
            public TaskTransition then(TaskTransition other) {
                throw new IllegalStateException("Cannot transition after success!");
            }

            @Override
            public String toString() {
                return "Success";
            }
        };


    }

    static TaskTransition mainTaskPerformed() {
        return new TaskTransition() {
            @Override
            public void performTransition(Task task) {
                task.setMainTaskPerformed();
            }

            @Override
            public String toString() {
                return "Main task performed";
            }
        };
    }

    static TaskTransition error(Exception ex) {
        return new TaskTransition() {
            @Override
            public void performTransition(Task task) {
                task.setError(ex);
                task.set(Task.State.FAILURE);
            }
            @Override
            public TaskTransition then(TaskTransition other) {
                throw new IllegalStateException("Cannot transition after error!");
            }

            @Override
            public String toString() {
                return "Error: " + ex.getMessage();
            }
        };
    }

    static TaskTransition waitForNtf(INotification notification) {
        return new TaskTransition() {
            @Override
            public void performTransition(Task task) {
                task.addWaitingCondition(t -> !t.getFieldContext().hasNotification(notification));
                task.set(Task.State.WAITING);
            }

            @Override
            public String toString() {
                return "Waiting for Notification: " + notification.getDescription();
            }
        };
    }

    static TaskTransition waitForField(String fieldname) {
        return new TaskTransition() {
            @Override
            public void performTransition(Task task) {
                task.addWaitingCondition(t -> {
                    boolean fieldNotPresent = !t.getFieldContext().hasField(fieldname);
                    return fieldNotPresent;
                });
                task.set(Task.State.WAITING);
            }

            @Override
            public String toString() {
                return "Waiting for Field: " + fieldname;
            }
        };
    }

    static TaskTransition pass() {
        return task -> { };
    }

}
