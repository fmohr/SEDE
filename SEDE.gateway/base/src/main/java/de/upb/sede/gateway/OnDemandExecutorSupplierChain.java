package de.upb.sede.gateway;

import de.upb.sede.exec.IExecutorHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OnDemandExecutorSupplierChain extends OnDemandExecutorSupplierAdapter {

    private final OnDemandExecutorSupplier priority;

    public OnDemandExecutorSupplierChain(OnDemandExecutorSupplier delegate, OnDemandExecutorSupplier priority) {
        super(delegate);
        this.priority = priority;
    }

    @Override
    public boolean isSupported(String service) {
        return (priority.isSupported(service) || super.isSupported(service));
    }

    @Override
    public List<IExecutorHandle> supplyWithService(String service) {
        List<IExecutorHandle> supply = new ArrayList<>(priority.supplyWithService(service));
        supply.addAll(super.supplyWithService(service));
        return supply;
    }

    @Override
    public List<String> supportedServices() {
        List<String> supportedServices = new ArrayList<>();
        supportedServices.addAll(priority.supportedServices());
        supportedServices.addAll(super.supportedServices());
        return supportedServices;
    }

    @Override
    public String getIdentifier() {
        throw new RuntimeException();
    }

    @Override
    public Optional<IExecutorHandle> supplyWithExecutorId(String executorId) {
        Optional<IExecutorHandle> iExecutorHandle = priority.supplyWithExecutorId(executorId);
        if(iExecutorHandle.isPresent()) {
            return iExecutorHandle;
        } else {
            return super.supplyWithExecutorId(executorId);
        }
    }

}
