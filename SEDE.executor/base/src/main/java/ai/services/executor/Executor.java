package ai.services.executor;

import ai.services.exec.*;
import ai.services.execution.GraphTaskExecution;
import ai.services.beta.ExecutorRegistration;
import ai.services.beta.IExecutorRegistration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Executor {

    private final AccessControlQueue acq;

    private final IExecutorConfiguration configuration;

    private List<Runnable> closingHooks = new ArrayList<>();

    public Executor(AccessControlQueue acq, IExecutorConfiguration configuration) {
        this.acq = Objects.requireNonNull(acq);
        this.configuration = Objects.requireNonNull(configuration);
        Objects.requireNonNull(configuration.getExecutorId(), "No executor id was specified. Generate executor id.");
    }

    public AccessControlQueue acq() {
        return acq;
    }

    protected IExecutorConfiguration getConfiguration() {
        return configuration;
    }

    public IExecutorContactInfo contactInfo() {
        return ExecutorContactInfo.builder()
            .qualifier(configuration.getExecutorId())
            .build();
    }

    public IExecutorRegistration registration() {
        return ExecutorRegistration.builder()
            .executorHandle(ExecutorHandle.builder()
                .contactInfo(contactInfo())
                .capabilities(ExecutorCapabilities.builder()
                    .services(configuration.getServices())
                    .features(configuration.getCapabilities())
                    .build())
                .build()
            ).build();
    }

    public IExecutorConfiguration configuration() {
        return configuration;
    }

    public void addShutdownHook(Runnable hook) {
        this.closingHooks.add(hook);
    }


    public void shutdown() {
        acq.compute(() -> {
            acq.close();
            Iterator<GraphTaskExecution> it = acq.iterate();
            while(it.hasNext()) {
                GraphTaskExecution execution = it.next();
                execution.interruptExecution();
            }
        });
        closingHooks.forEach(Runnable::run);
    }

}
