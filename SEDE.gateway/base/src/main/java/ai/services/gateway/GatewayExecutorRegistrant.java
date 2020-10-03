package ai.services.gateway;

import ai.services.IServiceRef;
import ai.services.SDLLookupService;
import ai.services.beta.IExecutorRegistration;
import ai.services.exec.IExecutorHandle;
import ai.services.interfaces.ExecutorRegistrant;
import ai.services.util.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class GatewayExecutorRegistrant implements ExecutorRegistrant {

    private static final Logger logger = LoggerFactory.getLogger(GatewayExecutorRegistrant.class);

    private final Cache<SDLLookupService> lookupServiceCache;

    private final ExecutorArbiter executorArbiter;

    public GatewayExecutorRegistrant(Cache<SDLLookupService> lookupServiceCache, ExecutorArbiter executorArbiter) {
        this.lookupServiceCache = lookupServiceCache;
        this.executorArbiter = executorArbiter;
        if(lookupServiceCache.access() == null) {
            logger.warn("An executor registrant was provided no lookup service to check registering services." +
                " No checking will be performed.");
        }
    }

    protected boolean checkServices(IExecutorRegistration registration){
        if(lookupServiceCache.access() == null) {
            return true;
        }
        /*
         * Remove all the supported Services from the executor that are not supported by
         * this gateway:
         */
        List<String> services = registration.getExecutorHandle().getCapabilities().getServices();
        SDLLookupService lookupService = lookupServiceCache.get();
        List<String> unSupportedService = services.stream().filter(service -> !lookupService.lookup(IServiceRef.of(null, service)).isPresent())
            .collect(Collectors.toList());

        if(logger.isWarnEnabled() && !unSupportedService.isEmpty()) {
            logger.warn("Executor registered with services that are unknown to the gateway. " +
                    "These services will be ignored:\n\t{}", String.join("\n\t", unSupportedService));
        }

        if(unSupportedService.size() == services.size()) {
            /*
             * as this implementation doesn't support loading services onto the executor,
             * registration with empty services are denied.
             */
            logger.warn("Executor tried to register with 0 amount of supported services. " +
                "Registration was not denied." +
                " Executors id: {}", registration.getExecutorHandle().getQualifier());
        }
        return true;
    }

    ExecutorArbiter getExecutorArbiter() {
        return executorArbiter;
    }

    @Override
    public boolean register(IExecutorRegistration registry) {
        String id = registry.getExecutorHandle().getQualifier();
        if(executorArbiter.hasExecutor(id)) {
            /*
             * Update the internal data for the executorId.
             * An executor may have changed some its information
             * like a new address in contact info map or has dropped support for a service.
             * Delete the internal representation of the executor.
             */
            logger.warn("ExecutorRegistration with an id that has already been registered: {} \nReplacing executor handle.",  id);
            executorArbiter.removeExecutor(id);
        }
        IExecutorHandle execHandle = registry.getExecutorHandle();

        if(!checkServices(registry)) {
            /*
             * as this implementation doesn't support loading services onto the executor, registration with empty services are denied.
             */
            logger.warn("Denied registration. Executors host: {}", execHandle.getQualifier());
            return false;

        }  else {
            StandaloneExecutor standaloneExecutor = new StandaloneExecutor(execHandle);
            executorArbiter.addSupplier(standaloneExecutor);
            logger.info("Executor registered successfully with {} services. Executor's id: {}", execHandle.getCapabilities().getServices().size(), id);
            logger.trace("Supported service of executor with id {} are {}.", id, execHandle.getCapabilities().getServices());
            return true;
        }
    }

}
