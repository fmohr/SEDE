package de.upb.sede.edd.deploy.deplengine;

import de.upb.sede.edd.DirLockAlreadyAcquiredException;
import de.upb.sede.edd.EDD;
import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.deploy.DeploymentContext;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.edd.deploy.ServiceDeployment;
import de.upb.sede.edd.deploy.model.DeploymentSpecification;
import de.upb.sede.edd.deploy.specsrc.SpecSource;
import de.upb.sede.util.MutableOptionalField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.Callable;

class InstallationEnv {

    private final static Logger logger = LoggerFactory.getLogger(InstallationEnv.class);

    private final EDD daemon;

    private final SpecSource specSource;

    private final Set<String> requestedServices = new HashSet<>();

    private final Set<DeploymentSpecification> deployments = new HashSet<>();

    private final MutableOptionalField<Installations> latestInstallations = MutableOptionalField.empty();

    public InstallationEnv(EDD daemon, SpecSource source) {
        this.daemon = daemon;
        this.specSource = source;
    }

    private LockableDir getLocalDir() {
        return daemon.getHome().getLocalDeplDir().getChild(specSource.getName());
    }

    private LockableDir getInstallDir() {
        return getLocalDir().getChild("installations");
    }

    private <T> T synchronizeCall(Callable<T> callable, boolean wait) {
        try (AutoCloseable groupLock = getLocalDir().lockDir(!wait)){
            return callable.call();
        } catch (DeploymentException ex) {
            throw ex;
        } catch ( DirLockAlreadyAcquiredException e) {
            throw new DeploymentException("Another local deployment call is in process.");
        } catch (Exception e) {
            throw new DeploymentException(e);
        }
    }

    void addRequestedService(String service) {
        addRequestedServices(Collections.singletonList(service));
    }

    void addRequestedServices(Collection<String> services) {
        logger.info("New services {} were requested from src {}.", services, specSource.getName());
        synchronizeCall(() -> {
            Set<DeploymentSpecification> requestedSpec = specSource
                .access()
                .collect(services, true, false);
            requestedServices.addAll(services);
            deployments.addAll(requestedSpec);
            return null;
        }, true);
        logger.info("Requested services were accepted. All requested services from src : \n {} \n are:\n {}", specSource.getName(), requestedServices);
    }

    void install(boolean update) {
        synchronizeCall(() -> {
            String installlationDisplayName = " {src:'" + specSource.getName() + "'} installation: ";
            Installations installations = new Installations(
                installlationDisplayName,
                getInstallDir(),
                specSource.access(),
                deployments,
                update);
            latestInstallations.set(installations);
            installations.start();
            return null;
        }, true);
    }

    List<InstallationReport> getCurrentState() {
        List<InstallationReport> states = new ArrayList<>();
        List<DeploymentSpecification> specs = new ArrayList<>(deployments);
        if(latestInstallations.isPresent()) {
            Installations installations = latestInstallations.get();
            Optional<DeploymentContext> deploymentContext = installations.lastDeployment();
            if(deploymentContext.isPresent()) {
                for (ServiceDeployment serviceDeployment : deploymentContext.get().getDeployment()) {
                    boolean removed = specs.remove(serviceDeployment.getSpecification());

                    if (!removed) {
                        continue;
                    }

                    InstallationReport state = serviceDeployment.state();
                    state.setRequestedServices(requestedServicesForSpec(serviceDeployment.getSpecification()));

                    state.setSpecSource(specSource.getName());

                    String stdOut = new String(serviceDeployment.getOutBytes().toByteArray());
                    String errOut = new String(serviceDeployment.getOutBytes().toByteArray());

                    state.setOut(stdOut);
                    state.setErr(errOut);

                    states.add(state);

                }
            }
        }
        specs.forEach(undeployedSpec -> {
            InstallationReport state = new InstallationReport();
            state.setServiceCollectionName(undeployedSpec.getName());
            state.setIncludedServices(undeployedSpec.getServices());
            state.setRequestedServices(requestedServicesForSpec(undeployedSpec));
            state.setState(InstallationState.Init);
            states.add(state);
        });
        return states;
    }

    private List<String> requestedServicesForSpec(DeploymentSpecification spec ){
        ArrayList<String> requestedServices = new ArrayList<>(spec.getServices());
        if(this.requestedServices.stream().anyMatch(spec)){
            /*
             * The spec name or one of its aliases were requested.
             * Therefore all of its services are implicitly requested.
             */
            return requestedServices;
        } else {
            requestedServices.retainAll(this.requestedServices);
            return requestedServices;
        }
    }

    public SpecSource getSpecSource() {
        return specSource;
    }

    public Set<String> getRequestedServices() {
        return requestedServices;
    }
}
