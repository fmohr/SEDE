package de.upb.sede.gateway;

import de.upb.sede.exec.IExecutorHandle;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class StandaloneExecutor implements OnDemandExecutorSupplier {

    private final IExecutorHandle handle;

    public StandaloneExecutor(IExecutorHandle handle) {
        this.handle = Objects.requireNonNull(handle);
    }

    public IExecutorHandle getHandle() {
        return handle;
    }

    @Override
    public boolean isSupported(String service) {
        return handle.getCapabilities().getServices().contains(Objects.requireNonNull(service));
    }

    @Override
    public List<IExecutorHandle> supply(String service) {
        if(isSupported(service)) {
            return Collections.singletonList(handle);
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
    public List<IExecutorHandle> allHandles() {
        return Collections.singletonList(handle);
    }

    @Override
    public Optional<IExecutorHandle> getHandle(String executorId) {
        if(handle.getContactInfo().getQualifier().equals(executorId)) {
            return Optional.of(handle);
        } else {
            return Optional.empty();
        }
    }
}
