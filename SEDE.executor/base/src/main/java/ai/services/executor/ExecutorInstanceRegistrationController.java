package ai.services.executor;

public interface ExecutorInstanceRegistrationController {

    void register(Executor executor);

    static ExecutorInstanceRegistrationController chain(
        ExecutorInstanceRegistrationController... registrationCtrls) {
        if(registrationCtrls == null || registrationCtrls.length == 0) {
            throw new IllegalArgumentException("Null or empty registrationCtrls");
        }

        return executor -> {
            for (ExecutorInstanceRegistrationController registrationCtrl : registrationCtrls) {
                registrationCtrl.register(executor);
            }
        };
    }

}
