package ai.services.executor.local;

import ai.services.executor.Executor;
import ai.services.executor.ExecutorInstanceRegistrationController;

public class LocalExecutorInstanceRegistrationCtrl implements ExecutorInstanceRegistrationController {

    private final LocalExecutorInstanceRegistry registry;

    public LocalExecutorInstanceRegistrationCtrl() {
        registry = LocalExecutorInstanceRegistry.INSTANCE;
    }

    public LocalExecutorInstanceRegistrationCtrl(LocalExecutorInstanceRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void register(Executor executor) {
        if(registry != null) {
            registry.put(executor.registration().getExecutorHandle().getQualifier(), executor);
        }
    }
}
