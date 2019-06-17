package de.upb.sede.edd.deploy.group;

import de.upb.sede.edd.DirLockAlreadyAcquiredException;
import de.upb.sede.edd.EDDHome;
import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.deploy.*;
import de.upb.sede.edd.deploy.group.transaction.AddServicesTransaction;
import de.upb.sede.edd.deploy.group.transaction.FixedServicesTransaction;
import de.upb.sede.edd.deploy.group.transaction.GroupComponents;
import de.upb.sede.edd.deploy.group.transaction.SetGatewayAddressTransaction;
import de.upb.sede.edd.deploy.model.DeploymentSpecification;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import de.upb.sede.util.Cache;
import de.upb.sede.util.LazyAccessCache;
import de.upb.sede.util.MutableOptionalField;
import de.upb.sede.util.Uncheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class LocalDeploymentGroup extends AbstractDeploymentGroup {

    private final static Logger logger = LoggerFactory.getLogger(LocalDeploymentGroup.class);

    private MutableOptionalField<String> gatewayAddress = MutableOptionalField.empty();

    private Set<String> requestedServices = new HashSet<>();
    private Set<DeploymentSpecification> deployedSpecs = new HashSet<>();
    private Cache<DeploymentSpecificationRegistry> registry;

//    private MutableOptionalField<DeploymentContext> lastDeployment = MutableOptionalField.empty();
    private LazyAccessCache<SingleInstanceGroupComponents> componentInstances;

    private LazyAccessCache<ExecutorService> executorService = new LazyAccessCache<>(Executors::newCachedThreadPool);



    private EDDHome home;

    public LocalDeploymentGroup(String groupName, EDDHome home, Cache<DeploymentSpecificationRegistry> registry) {
        super(groupName, home.getGroupDir(groupName));
        this.registry = Objects.requireNonNull(registry);
        this.home = Objects.requireNonNull(home);
        this.componentInstances = new LazyAccessCache<>(() ->
            new SingleInstanceGroupComponents(groupName, executorService.access(), home));
    }

    @Override
    public void commitAdditionalServices(AddServicesTransaction transaction) {
        boolean wait = transaction.getSettings().waitInQueue();
        try (AutoCloseable groupLock = getGroupDir().lockDir(!wait)){
            digestServices(transaction.getServices(), transaction.getSettings().isIncurIncludedServices());
            deploy(transaction.getSettings());
        } catch (DeploymentException ex) {
            throw ex;
        } catch ( DirLockAlreadyAcquiredException e) {
            throw new DeploymentException(getDisplayName() + " another deployment transaction is in process.");
        } catch (Exception e) {
            throw new DeploymentException(e);
        }
    }

    @Override
    public void commitFixServices(FixedServicesTransaction transaction) {
        deployedSpecs.clear();
        requestedServices.clear();;
        boolean wait = transaction.getSettings().waitInQueue();
        try (AutoCloseable groupLock = getGroupDir().lockDir(!wait)){
            digestServices(transaction.getServices(), transaction.getSettings().isIncurIncludedServices());
            deploy(transaction.getSettings());
        } catch (DeploymentException ex) {
            throw ex;
        } catch ( DirLockAlreadyAcquiredException e) {
            throw new DeploymentException(getDisplayName() + " another deployment transaction is in process.");
        } catch (Exception e) {
            throw new DeploymentException(e);
        }
    }

    @Override
    public void commitStop() {
        stop();
    }

    @Override
    public void commitStart() {
        start();
    }

    @Override
    public void commitSetGatewayAddress(SetGatewayAddressTransaction transaction) {
        boolean wait = true;
        try (AutoCloseable groupLock = getGroupDir().lockDir(!wait)){
            gatewayAddress.setNullable(transaction.getGateway().orElse(null));
            if(gatewayAddress.isPresent() &&  componentInstances.access().hasJavaExecutor()) {
                componentInstances.access().getJavaExecutor().registerToGateway(gatewayAddress.get());
            }
        } catch (DeploymentException ex) {
            throw ex;
        } catch ( DirLockAlreadyAcquiredException e) {
            throw new DeploymentException(getDisplayName() + " another deployment transaction is in process.");
        } catch (Exception e) {
            throw new DeploymentException(e);
        }
    }


    public void start() {
        try (AutoCloseable groupLock = getGroupDir().lockDir(false)){
            deploy(new DeploymentWorkflowSettings());
        } catch (DeploymentException ex) {
            throw ex;
        } catch ( DirLockAlreadyAcquiredException e) {
            throw new DeploymentException(getDisplayName() + " another deployment transaction is in process.");
        } catch (Exception e) {
            throw new DeploymentException(e);
        }
    }

    public void stop() {
        try (AutoCloseable groupLock = getGroupDir().lockDir(false)){
            executorService.access().shutdownNow();
            executorService.unset();
            if(componentInstances.access().hasJavaExecutor())
                componentInstances.access().getJavaExecutor().stop();
            componentInstances.unset();
        } catch (DeploymentException ex) {
            throw ex;
        } catch ( DirLockAlreadyAcquiredException e) {
            throw new DeploymentException(getDisplayName() + " another deployment transaction is in process.");
        } catch (Exception e) {
            throw new DeploymentException(e);
        }
    }

    private void deploy(DeploymentWorkflowSettings settings) {
        DeploymentContext context = new DeploymentContext(settings);
        try {
            deploy(context);
        } catch (Throwable throwable) {
            throw new DeploymentException(getDisplayName() + " error while deploying services.", throwable);
        } finally {
            context.getExecutor().shutdownNow();
        }

    }


    private void deploy(DeploymentContext context) {
        DeploymentWorkflow workflow =
            DeploymentWorkflow.createWorkflow(context, registry.access(),
                deployedSpecs.stream().map(DeploymentSpecification::getName).collect(Collectors.toSet()));
        workflow.deploy(home.getHomeDir().getChild("services"));
        workflow.getContext().collectOutputs(componentInstances.access());
        if(componentInstances.access().hasJavaExecutor()) {
            componentInstances.access().getJavaExecutor().getExecutorConfig().getServices().retainAll(requestedServices);
            componentInstances.access().getJavaExecutor().start();
            if(gatewayAddress.isPresent()) {
                componentInstances.access().getJavaExecutor().registerToGateway(gatewayAddress.get());
            }
        }
    }



    public GroupComponents getComponentInstances() {
        return componentInstances.access();
    }

    private void digestServices(List<String> services, boolean incurIncludedServices) {
        Set<String> requested = new HashSet<>();
        Set<DeploymentSpecification> newSpecs = registry.access().collect(services, true, false);

        for(String requestedService : services) {
            for (DeploymentSpecification spec : newSpecs) {
                if(spec.test(requestedService)) {
                    requested.addAll(spec.getServices());
                    break;
                }
                else if(spec.getServices().contains(requestedService)){
                    if(incurIncludedServices) {
                        requested.addAll(spec.getServices());
                    } else {
                        requested.add(requestedService);
                    }
                    break;
                }
            }
        }

        deployedSpecs.addAll(newSpecs);
        requestedServices.addAll(requested);
    }

    public MutableOptionalField<String> getGatewayAddress() {
        return gatewayAddress;
    }

    public Set<String> getRequestedServices() {
        return requestedServices;
    }
}
