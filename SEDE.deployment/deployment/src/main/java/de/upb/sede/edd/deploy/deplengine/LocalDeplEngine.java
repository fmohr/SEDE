package de.upb.sede.edd.deploy.deplengine;

import de.upb.sede.edd.EDD;
import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.deploy.AscribedService;
import de.upb.sede.edd.deploy.SpecURI;
import de.upb.sede.edd.deploy.specsrc.SpecSource;
import de.upb.sede.edd.deploy.target.JExecutorTarget;
import de.upb.sede.edd.runtime.ExecutorRuntime;
import de.upb.sede.edd.runtime.ExecutorRuntimeSupplier;
import de.upb.sede.requests.deploy.ExecutorDemandFulfillment;
import de.upb.sede.requests.deploy.ExecutorDemandRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void addServices(List<AscribedService> services) {
        for(AscribedService ascribedService : services) {
            SpecURI specUri = ascribedService.getSpecUri();
            SpecSource source = daemon.getSpecSrc().findOrCreate(specUri);
            InstallationEnv installationEnv = findOrCreateEnv(source);
            installationEnv.addRequestedService(ascribedService.getService());
        }
    }

    @Override
    public void prepareDeployment(boolean update) {
        for(InstallationEnv env : envs) {
            env.install(update);
        }
    }

    @Override
    public List<InstallationReport> getCurrentState() {
        List<InstallationReport> states = new ArrayList<>();
        for(InstallationEnv env : envs) {
            states.addAll(env.getCurrentState());
        }
        return states;
    }

    @Override
    public void removeServices(List<AscribedService> services) {

    }

    @Override
    public ExecutorDemandFulfillment demand(ExecutorDemandRequest request) {
        ExecutorDemandFulfillment fulfillment = new ExecutorDemandFulfillment();

        List<String> requestedServices = new ArrayList<>(request.getServices());

        if (request.isSet(ExecutorDemandRequest.SatMode.Reuse)) {
            requestedServices.removeIf(service -> daemon
                .getRuntimeRegistry()
                .getAllRunning()
                .anyMatch(er ->
                    er.getTarget().getExecutorConfig().getServices().contains(service)));
        }

        for(InstallationEnv env : envs) {
            if(!env.getSpecSource().getServiceNamespace().equals(request.getServiceNamespace())) {
                continue;
            }
            if (!env.getCurrentState().stream().allMatch(state ->
                state.getState() == InstallationState.Success)) {
                continue;
            }
            boolean requestedServiceFound = requestedServices.removeIf(s ->
                env.getRequestedServices().contains(s));

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
        return supplier;
    }


}
