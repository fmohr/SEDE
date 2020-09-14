package de.upb.sede.gateway;

import de.upb.sede.exec.IExecutorHandle;

import java.util.List;
import java.util.Optional;

public abstract class OnDemandExecutorSupplierAdapter implements OnDemandExecutorSupplier {

    private final OnDemandExecutorSupplier delegate;

    protected OnDemandExecutorSupplierAdapter(OnDemandExecutorSupplier delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean isSupported(String service) {
        return delegate.isSupported(service);
    }

    @Override
    public List<IExecutorHandle> supplyWithService(String service) {
        return delegate.supplyWithService(service);
    }

    @Override
    public List<String> supportedServices() {
        return delegate.supportedServices();
    }

    @Override
    public String getIdentifier() {
        return delegate.getIdentifier();
    }


    @Override
    public Optional<IExecutorHandle> supplyWithExecutorId(String executorId) {
        return delegate.supplyWithExecutorId(executorId);
    }
}
