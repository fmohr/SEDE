package de.upb.sede.composition.graphs;

import de.upb.sede.SDLLookupService;
import de.upb.sede.exec.IExecutorHandle;
import de.upb.sede.gateway.ExecutorSupplyCoordinator;
import de.upb.sede.requests.resolve.beta.IResolveRequest;

/**
 * This class bundles all classes whose pointers are needed for resolving a
 * composition graph.
 */
public class ResolveInfo_1 {

    private final SDLLookupService lookupService;

    private final ExecutorSupplyCoordinator executorSupplyCoordinator;

    private final IResolveRequest request;

    public ResolveInfo_1(SDLLookupService lookupService, ExecutorSupplyCoordinator executorSupplyCoordinator, IResolveRequest clientExecutor) {
        this.lookupService = lookupService;
        this.executorSupplyCoordinator = executorSupplyCoordinator;
        this.request = clientExecutor;
    }

    public SDLLookupService getLookupService() {
        return lookupService;
    }

    public ExecutorSupplyCoordinator getExecutorSupplyCoordinator() {
        return executorSupplyCoordinator;
    }

    public IExecutorHandle getClientExecutor() {
        return request.getClientExecutorRegistration().getExecutorHandle();
    }

}
