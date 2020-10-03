package ai.services.executor;

import ai.services.interfaces.ExecutorRegistrant;

public class GatewayLocalRegistration implements ExecutorInstanceRegistrationController{

    private final ExecutorRegistrant registrant;

    public GatewayLocalRegistration(ExecutorRegistrant registrant) {
        this.registrant = registrant;
    }

    @Override
    public void register(Executor executor) {
        if(registrant != null) {
            registrant.register(executor.getRegistration());
        }
    }
}
