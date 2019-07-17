package de.upb.sede.gateway.edd;

import de.upb.sede.gateway.ExecutorHandle;
import de.upb.sede.gateway.OnDemandExecutorSupplier;
import de.upb.sede.requests.deploy.EDDRegistration;
import de.upb.sede.util.ExpiringCache;
import de.upb.sede.util.RandomArbiter;
import de.upb.sede.util.SystemSettingLookup;
import de.upb.sede.util.TTLCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CachedExecutorHandleSupplier implements OnDemandExecutorSupplier {

    private final static Logger logger = LoggerFactory.getLogger(CachedExecutorHandleSupplier.class);

    private static final long SERVICE_REQUEST_EXPIRATION_MINUTES;

    private static final long EXECUTOR_HANDLE_REFRESH_SECONDS;

    static {

        String setting = new SystemSettingLookup(
            "3",
            "de.upb.sede.gateway.edd.ServiceExpirationTimeoutMinutes",
            "GATEWAY_SERVICE_EXPIRATION_TIMEOUT").access();
        long expiration;
        try {
            expiration = Long.parseLong(setting);
            if(expiration < 1) {
                expiration = 1;
            }
        } catch (NumberFormatException e) {
            expiration = 3;
        }

        setting = new SystemSettingLookup(
            "10",
            "de.upb.sede.gateway.edd.ExecutorHandleRefreshSeconds",
            "GATEWAY_EXECUTOR_HANDLE_REFRESH_SECONDS").access();

        long refresh;
        try {
            refresh = Long.parseLong(setting);
            if(refresh < 10) {
                refresh = 10;
            }
        } catch (NumberFormatException e) {
            refresh = 30;
        }

        SERVICE_REQUEST_EXPIRATION_MINUTES = expiration;
        EXECUTOR_HANDLE_REFRESH_SECONDS = refresh;
    }


    private EDDExecutorSupplier supplier;

    private TTLCache<List<ExecutorHandle>> execHandleCache = new TTLCache<>(
        EXECUTOR_HANDLE_REFRESH_SECONDS,
        TimeUnit.SECONDS,
        this::retrieveExecHandles);

    private List<ExpiringCache<String>> requestedServices = new ArrayList<>();


    private Function<List<ExecutorHandle>, Optional<ExecutorHandle>> executorArbiter =
        new RandomArbiter<>();


    public CachedExecutorHandleSupplier(EDDRegistration eddRegistration) {
        this.supplier = new EDDExecutorSupplier(eddRegistration);
    }

    private List<ExecutorHandle> retrieveExecHandles() {
        List<String> requestedServices = collectRequestedServices();
        try {
            return supplier.supplyList(collectRequestedServices());
        } catch (UnsuppliedExecutorException ex) {
            logger.warn("Error demanding requested services: \n{}\n from {}.", requestedServices, supplier.getEDDDisplayName(), ex);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public boolean isSupported(String service) {
        return supplier.isSupported(service);
    }

    private synchronized void cleanupExpiredRequests() {
        requestedServices.removeIf(ExpiringCache::isExpired);
    }

    private synchronized boolean prolongServiceRequest(String service) {
        List<ExpiringCache<String>> requestedServices = this.requestedServices.stream()
            .filter(cache -> service.equals(cache.get()))
            .collect(Collectors.toList());
        if(requestedServices.isEmpty()) {
            return false;
        }
        requestedServices.forEach(ExpiringCache::prolong);
        return true;
    }

    private synchronized void addService(String service) {
        ExpiringCache<String> serviceRequestCache;
        serviceRequestCache = new ExpiringCache<>(
            SERVICE_REQUEST_EXPIRATION_MINUTES, TimeUnit.MINUTES, service);
        requestedServices.add(serviceRequestCache);
    }

    private synchronized List<String> collectRequestedServices() {
        return requestedServices.stream()
            .filter(c -> !c.isExpired())
            .map(ExpiringCache::forceAccess)
            .collect(Collectors.toList());
    }

    @Override
    public ExecutorHandle supply(String service) {
        /*
         * TODO recheck if there are any cache miss problems here.
         *  Are the caches cleaned up?
         */
        cleanupExpiredRequests();
        boolean servicePreviouslyRequested = prolongServiceRequest(service);
        if(servicePreviouslyRequested) {
            addService(service);
        }
        List<ExecutorHandle> executorHandles = execHandleCache.access().stream()
            .filter(handle ->
                handle.getExecutionerCapabilities()
                    .supportedServices().contains(service))
            .collect(Collectors.toList());

        return executorArbiter.apply(executorHandles)
            .orElseThrow(()
                -> new UnsuppliedExecutorException("Service " + service
                + "  wasn't supplied by a demand request to "
                + supplier.getEDDDisplayName()));
    }

    @Override
    public List<String> supportedServices() {
        return supplier.supportedServices();
    }

    @Override
    public String getIdentifier() {
        return supplier.getIdentifier();
    }

    @Override
    public List<ExecutorHandle> allHandles() {
        return Collections.unmodifiableList(execHandleCache.access());
    }

    @Override
    public Optional<ExecutorHandle> getHandle(String executorId) {
        return execHandleCache.access()
            .stream()
            .filter(handle -> handle.getExecutorId().equals(executorId))
            .findAny();
    }

}
