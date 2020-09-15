package ai.services.executor.local;

import ai.services.executor.Executor;
import ai.services.executor.ExecutorInstanceRegistrationController;
import ai.services.interfaces.ExecutorRegistrant;

import java.util.Objects;

public class LocalRegistrantCtrl implements ExecutorInstanceRegistrationController {

    private final ExecutorRegistrant executorRegistrant;

    public LocalRegistrantCtrl(ExecutorRegistrant executorRegistrant) {
        this.executorRegistrant = Objects.requireNonNull(executorRegistrant);
    }

    @Override
    public void register(Executor executor) {

    }
}
