package ai.services.gateway;

import ai.services.SDLLookupService;
import ai.services.util.Cache;

public class InProcessExecutorRegistrant extends GatewayExecutorRegistrant {


    public InProcessExecutorRegistrant(Cache<SDLLookupService> lookupServiceCache, ExecutorArbiter supplyCoordinator) {
        super(lookupServiceCache, supplyCoordinator);
    }
}
