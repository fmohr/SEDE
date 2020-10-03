package ai.services.gateway;

import ai.services.*;
import ai.services.interfaces.ExecutorRegistrant;
import ai.services.util.Cache;
import ai.services.util.DeepImmutableCopier;
import ai.services.util.StaticCache;
import ai.services.util.TTLCache;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class GatewayFactory {

    private ExecutorArbiter arbiter;

    private Cache<SDLLookupService> lookupServiceCache;

    private MutableSDLAssembly sdlAssembly;

    private GatewayExecutorRegistrant gatewayExecutorRegistrant;

    private StdGatewayImpl gateway;

    public void addServiceAssembly(ISDLAssembly addedAssembly) {
        if(sdlAssembly == null) {
            sdlAssembly = MutableSDLAssembly.create();
        }
        sdlAssembly.addAllCollections(addedAssembly.getCollections());
    }

    public GatewayFactory setLookUpService(SDLLookupService lookUpService) {
        this.lookupServiceCache = new StaticCache<>(lookUpService);
        return this;
    }

    public GatewayFactory setAutoRefreshingLookupService(SDLLookupService lookupService) {
        this.lookupServiceCache = new TTLCache<>(10, TimeUnit.SECONDS, () ->
            new SDLCacheLookupService(lookupService));
        return this;
    }

    public GatewayFactory useInProcessRegistrant() {
        arbiter = InProcessExecutorArbiter.EXECUTOR_ARBITER_INSTANCE;
        return this;
    }

    public StdGatewayImpl build() {
        if(gateway != null) {
            return gateway;
        }
        gateway = new StdGatewayImpl(getOrCreateLookupService(), getOrCreateArbiter());
        buildRegistrant();
        return gateway;
    }

    public ExecutorRegistrant buildRegistrant() {
        if(gatewayExecutorRegistrant != null) {
            return gatewayExecutorRegistrant;
        }
        gatewayExecutorRegistrant = new GatewayExecutorRegistrant(getOrCreateLookupService(),
            getOrCreateArbiter());
        return gatewayExecutorRegistrant;
    }

    private ExecutorArbiter getOrCreateArbiter() {
        if(arbiter == null) {
            arbiter = new ExecutorArbiter();
        }
        return arbiter;
    }

    private Cache<SDLLookupService> getOrCreateLookupService() {
        if (lookupServiceCache != null) {
            return lookupServiceCache;
        }
        if(sdlAssembly != null) {
            ISDLAssembly immutableAssembly = DeepImmutableCopier.copyAsImmutable(sdlAssembly, ISDLAssembly.class);
            lookupServiceCache = new StaticCache<>(
                new SDLCacheLookupService(
                    new SDLBaseLookupService(immutableAssembly)));
        }
        else {
            lookupServiceCache = new StaticCache<>(new SDLBaseLookupService(Collections::emptyList));
        }
        return lookupServiceCache;
    }



}
