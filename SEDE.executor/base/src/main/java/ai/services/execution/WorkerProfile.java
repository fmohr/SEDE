package ai.services.execution;

/**
 * Workers carry a WorkerProfile instance with them that determines what sort of tasks they are set to handle.
 */
public interface WorkerProfile {

    public boolean isInGroup(String taskGroup);

    /**
     * Workers with this profile can handle all tasks.
     */
    public static final WorkerProfile SUPER_STAR = taskGroup -> true;

}
