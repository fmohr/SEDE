package de.upb.sede.edd.deploy;

import com.google.common.collect.Ordering;
import de.upb.sede.edd.DirLockAlreadyAcquiredException;
import de.upb.sede.edd.LockableDir;
import de.upb.sede.edd.deploy.model.DeploymentSpecification;
import de.upb.sede.edd.deploy.model.DeploymentSpecificationRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DeploymentWorkflow {

    private final static Logger logger = LoggerFactory.getLogger(DeploymentWorkflow.class);

    private List<ServiceDeployment> steps;

    private DeploymentContext context;

    public DeploymentWorkflow(DeploymentContext context, List<ServiceDeployment> steps) {
        this.context = Objects.requireNonNull(context);
        this.steps = Objects.requireNonNull(steps);
    }

    public static DeploymentWorkflow createWorkflow(DeploymentSpecificationRegistry registry, List<String> serviceCollections) {
        DeploymentContext context = new DeploymentContext();
        return createWorkflow(context, registry, serviceCollections);
    }

    public static DeploymentWorkflow createWorkflow(DeploymentContext context, DeploymentSpecificationRegistry registry, Collection<String> serviceCollections) {
        Collection<DeploymentSpecification> toBeDeployed = registry.collect(serviceCollections,true, false);
        ArrayList<DeploymentSpecification> allDeployments = new ArrayList<>(registry.includeDependencies(toBeDeployed));
        allDeployments.sort(registry.order()); // sort by dependencies
        return new DeploymentWorkflow(context,
            allDeployments.stream()
            .map(spec -> new ServiceDeployment(context, spec))
            .collect(Collectors.toList()));
    }

    public List<ServiceDeployment> getSteps() {
        return steps;
    }

    public void deploy(LockableDir serviceDirs) {
        try(AutoCloseable closeable = serviceDirs.lockDir(!context.getSettings().waitInQueue())) {
            doDeploy(serviceDirs);
        } catch (DeploymentException ex) {
            throw ex;
        } catch (DirLockAlreadyAcquiredException ex) {
            throw new DeploymentException("Service directory is already locked: " + serviceDirs.toFile().toString() );
        } catch (Exception e) {
            Throwable cause = e;
            while((cause = cause.getCause()) != null) {
                if(cause instanceof DeploymentException) {
                    throw (DeploymentException) cause;
                }
            }
            throw new DeploymentException("Unexpected error.", e);
        }
    }

    public DeploymentContext getContext() {
        return context;
    }

    private void doDeploy(LockableDir serviceDirs) {
        for(ServiceDeployment deployment : steps) {
            LockableDir serviceDir = serviceDirs.getChild(deployment.getSpecification().getName());
            LockableDir methodDir = serviceDir.getChild(deployment.getDeploymentMethod().getMethodType());
            context.register(deployment);
            deployment.deploy(methodDir);
        }
    }

}
