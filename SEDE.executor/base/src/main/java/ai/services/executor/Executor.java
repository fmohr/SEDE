package ai.services.executor;

import ai.services.execution.AccessControlQueue;
import ai.services.execution.Execution;
import de.upb.sede.beta.ExecutorRegistration;
import de.upb.sede.beta.IExecutorRegistration;
import de.upb.sede.exec.*;

import java.util.Iterator;
import java.util.Objects;

public class Executor {

    private final AccessControlQueue acq;

    private final IExecutorConfiguration configuration;

    public Executor(AccessControlQueue acq, IExecutorConfiguration configuration) {
        this.acq = Objects.requireNonNull(acq);
        this.configuration = Objects.requireNonNull(configuration);
        Objects.requireNonNull(configuration.getExecutorId(), "No executor id was specified. Generate executor id.");
    }

    public AccessControlQueue acq() {
        return acq;
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

    public void shutdown() {
        acq.compute(() -> {
            acq.close();
            Iterator<Execution> it = acq.iterate();
            while(it.hasNext()) {
                Execution execution = it.next();
                execution.interruptExecution();
            }
        });
    }

}
