package ai.services.executor;

public interface ExecutorInstanceRegistrationController {

    void register(Executor executor);

    static ExecutorInstanceRegistrationController chain(
        ExecutorInstanceRegistrationController first,
        ExecutorInstanceRegistrationController second) {
        return executor -> {
            first.register(executor);
            second.register(executor);
        };
    }

}
