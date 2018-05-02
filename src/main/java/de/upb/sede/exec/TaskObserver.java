package de.upb.sede.exec;

/**
 * Implementations of this interface can observe a Task object.
 * When the state of the task object changes the taskUpdated will be invoked.
 *
 */
public interface TaskObserver {

    /**
     * This method is invoked when any of the observed tasks change their state.
     * The Task itself is given as an argument to the invokation.
     *
     * When implementing this function be aware of racing conditions.
     * There can be multiple threads in this method at once as the notifyObservers functions in Task is usually called by workers.
     *
     * @param updatedTask the task whose state is changed.
     */
    void taskUpdated(Task updatedTask);
}
