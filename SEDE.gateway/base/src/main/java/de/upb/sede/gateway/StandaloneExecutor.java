package de.upb.sede.gateway;

import de.upb.sede.exec.ExecutorHandle;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StandaloneExecutor implements OnDemandExecutorSupplier {

    private final ExecutorHandle handle;

    public StandaloneExecutor(ExecutorHandle handle) {
        this.handle = Objects.requireNonNull(handle);
    }

    public ExecutorHandle getHandle() {
        return handle;
    }

    @Override
    public boolean isSupported(String service) {
        return handle.getCapabilities().getServices().contains(Objects.requireNonNull(service));
    }

    @Override
    public ExecutorHandle supply(String service) {
        if(isSupported(service)) {
            return handle;
        } else {
            throw new IllegalArgumentException("Service '" + service + "' is not supported.");
        }
    }

    @Override
    public List<String> supportedServices() {
        return handle.getCapabilities().getServices();
    }

    @Override
    public String getIdentifier() {
        return handle.getContactInfo().getQualifier();
    }

    @Override
    public List<ExecutorHandle> allHandles() {
        return Collections.singletonList(handle);
    }

    @Override
    public Optional<ExecutorHandle> getHandle(String executorId) {
        if(handle.getContactInfo().getQualifier().equals(executorId)) {
            return Optional.of(handle);
        } else {
            return Optional.empty();
        }
    }
}
