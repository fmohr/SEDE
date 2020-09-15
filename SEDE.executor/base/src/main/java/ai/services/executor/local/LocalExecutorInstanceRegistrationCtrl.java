package ai.services.executor.local;

import ai.services.executor.Executor;
import ai.services.executor.ExecutorInstanceRegistrationController;

import java.util.Map;

public class LocalExecutorInstanceRegistrationCtrl implements ExecutorInstanceRegistrationController {

    private final Map<String, Executor> registry;

    public LocalExecutorInstanceRegistrationCtrl() {
        registry = LocalExecutorInstanceRegistry.INSTANCE;
    }

    public LocalExecutorInstanceRegistrationCtrl(Map<String, Executor> registry) {
        this.registry = registry;
    }

    @Override
    public void register(Executor executor) {
        if(registry != null) {
            registry.put(executor.registration().getExecutorHandle().getQualifier(), executor);
        }
    }
}
