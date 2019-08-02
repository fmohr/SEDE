package de.upb.sede.edd.deploy.deplengine;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.edd.deploy.specsrc.SpecSource;
import de.upb.sede.edd.reports.ServiceRequirementReport;
import de.upb.sede.edd.reports.ServiceRequirementStatus;
import de.upb.sede.edd.runtime.ExecutorRuntime;
import de.upb.sede.edd.runtime.ExecutorRuntimeSupplier;
import de.upb.sede.requests.deploy.ExecutorDemandFulfillment;
import de.upb.sede.requests.deploy.ExecutorDemandRequest;
import de.upb.sede.util.Either;
import de.upb.sede.util.UnmodifiableURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class LocalDeplEngine extends DeplEngine {

    private final static Logger logger = LoggerFactory.getLogger(LocalDeplEngine.class);

    private final EDD daemon;

    private List<InstallationEnv> envs = new ArrayList<>();

    public LocalDeplEngine(EDD daemon) {
        this.daemon = daemon;
    }

    @Override
    public String getName() {
        return DeplEngineRegistry.LOCAL_ENGINE_NAME;
    }

    private LockableDir getLocalDir() {
        return daemon.getHome().getLocalDeplDir();
    }

    Optional<InstallationEnv> findEnv(SpecSource specSrc) {
        return envs
            .stream()
            .filter(installationEnv -> installationEnv.getSpecSource().equals(specSrc))
            .findFirst();
    }

    InstallationEnv createEnv(SpecSource specSrc) {
        InstallationEnv env = new InstallationEnv(daemon, specSrc);
        envs.add(env);
        return env;
    }

    InstallationEnv findOrCreateEnv(SpecSource specSrc) {
        return findEnv(specSrc).orElse(createEnv(specSrc));
    }

    @Override
    public List<ServiceRequirementReport> addServices(List<AscribedService> services) {

        List<ServiceRequirementReport> reports = new ArrayList<>();

        Map<UnmodifiableURI, Either<SpecSource, String>> sources = new HashMap<>();

        services.stream()
            .map(AscribedService::getNamespace)
            .distinct()
            .forEach(specUri -> {
            try {
                SpecSource specSource = daemon.getSpecSrc().findOrCreate(specUri);
                sources.put(specUri, Either.left(specSource));
            } catch (Exception ex) {
                String err = String.format("Couldn't access source `%s`, error: %s", specUri.buildString(), ex.getMessage());
                sources.put(specUri, Either.right(err));
            }
        });


        Map<UnmodifiableURI, List<AscribedService>> requestedServices = services.stream().map(service -> {
            ServiceRequirementReport report = new ServiceRequirementReport(service);
            if (sources.containsKey(service.getNamespace())) {
                Either<SpecSource, String> source = sources.get(service.getNamespace());
                if (source.hasLeft()) {
                    return Optional.<AscribedService>of(service);
                } else {
                    report.addMessage(source.getRight());
                    report.setStatus(ServiceRequirementStatus.FAILED);
                }
            } else {
                report.addMessage("Source not found for service: " + service);
                report.setStatus(ServiceRequirementStatus.FAILED);
            }
            reports.add(report);
            return Optional.<AscribedService>empty();
        }).filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.groupingBy(AscribedService::getNamespace));


        for(UnmodifiableURI specUri : requestedServices.keySet()) {
            List<ServiceRequirementReport> installReports
                = requestedServices.get(specUri)
                .stream()
                .map(ServiceRequirementReport::new)
                .collect(Collectors.toList());
            try {
                SpecSource source = Objects.requireNonNull(sources.get(specUri).getLeft(),
                    "ERROR: " + sources.get(specUri).getRight());

                InstallationEnv installationEnv = findOrCreateEnv(source);
                installationEnv.addRequestedServices(
                    requestedServices.get(specUri).stream().map(AscribedService::getService).collect(Collectors.toList()));
                installReports.forEach(report -> report.setStatus(ServiceRequirementStatus.FETCHED));
            } catch (Exception ex) {
                String err = String.format("Requested services could not be added: %s",ex.getMessage());
                installReports.forEach(report -> report.addMessage(err));
                installReports.forEach(report -> report.setStatus(ServiceRequirementStatus.FAILED));
            }
            reports.addAll(installReports);
        }
        return reports;
    }


    @Override
    public void prepareDeployment(boolean update) {
        for(InstallationEnv env : envs) {
            try {
                env.install(update);
            } catch (Exception ex) {
                logger.warn("Couldnt prepare environemnt: {}", env.getSpecSource().getServiceNamespace(), ex);
            }
        }
    }

    @Override
    public List<InstallationReport> getCurrentState() {
        List<InstallationReport> states = new ArrayList<>();
        for(InstallationEnv env : envs) {
            try {
                states.addAll(env.getCurrentState());
            } catch(Exception ex) {
                logger.warn("Couldn't retrieve state from environment: {}", env.getSpecSource().getServiceNamespace(), ex);
            }
        }
        return states;
    }

    @Override
    public void removeServices(List<AscribedService> services) {

    }

    @Override
    public ExecutorDemandFulfillment demand(ExecutorDemandRequest request) {
        logger.info("Demand that deployment engine '{}' fullfills the request: {}.", getName(), request.getServices());
        ExecutorDemandFulfillment fulfillment = new ExecutorDemandFulfillment();

        List<AscribedService> requestedServices;
        try {
            requestedServices = request.getServices().stream()
                    .map(AscribedService::parseURI).collect(Collectors.toList());
        } catch(Exception ex) {
            logger.error("The requested services aren't ascribed with a namespace: ", ex);
            return fulfillment;
        }

        if (request.isSet(ExecutorDemandRequest.SatMode.Reuse)) {
            requestedServices.removeIf(service -> daemon
                .getRuntimeRegistry()
                .getAllRunning()
                .anyMatch(er ->
                    er.getTarget().getExecutorConfig().getServices().contains(service.getService())));
        }

        for(InstallationEnv env : envs) {
            if(requestedServices.stream().noneMatch(ascribedService ->
                env.getSpecSource().getServiceNamespace().equals(ascribedService.getNamespace().buildString())
            )) {
                continue;
            }
            if (!env.getCurrentState().stream().allMatch(state ->
                state.getState() == InstallationState.Success)) {
                continue;
            }
            boolean requestedServiceFound = requestedServices.removeIf(s ->
                env.getRequestedServices().contains(s.getService()));

            if(requestedServiceFound) {
                ExecutorRuntimeSupplier su = spawnNewExecutor(env);
                for(ExecutorRuntime er : su.getRuntimes().values()) {
                    daemon.getRuntimeRegistry().addExecutorRuntime(er);
                    fulfillment.getExecutors().add(er.getTarget().getRegistration());
                }
            }
        }
        return fulfillment;
    }

    private ExecutorRuntimeSupplier spawnNewExecutor(InstallationEnv env) {
        ExecutorRuntimeSupplier supplier = new ExecutorRuntimeSupplier(daemon, env.getSpecSource());
        try {
            env.getLatestInstallations().get().lastDeployment().get().collectOutputs(supplier);
        } catch (Exception ex) {
            logger.error("Error while creating runtimes for: {}", env, ex);
        }
        supplier.getRuntimes().values().forEach(runtime -> {
            runtime.getTarget().start();
        });
        return supplier;
    }

}
