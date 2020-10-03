package de.upb.sede.edd.deploy;


import de.upb.sede.edd.runtime.ExecutorRuntimeSupplier;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeploymentContext {

    private List<ServiceDeployment> deployments = new ArrayList<>();

    private DeploymentWorkflowSettings settings = new DeploymentWorkflowSettings();

    private Cache<ExecutorService> executor = new LazyAccessCache<>(Executors::newCachedThreadPool);


    public DeploymentContext(DeploymentWorkflowSettings settings, ExecutorService executorService) {
        this.executor = new StaticCache<>(executorService);
        this.settings = settings;
    }

    public DeploymentContext(DeploymentWorkflowSettings settings) {
        this.settings = settings;
    }

    public DeploymentContext() {
    }

    public void register(ServiceDeployment deployment) {
        this.deployments.add(Objects.requireNonNull(deployment));
    }

    public List<ServiceDeployment> getDeployment() {
        return deployments;
    }

    public ServiceDeployment findByName(String name) {
        for(ServiceDeployment deployment : deployments) {
            if(deployment.getSpecification().test(name)) {
                return deployment;
            }
        }
        throw new DeploymentException(String.format("Cannot find service deployment %s.", name));
    }

    public DeploymentWorkflowSettings getSettings() {
        return settings;
    }

    public ExecutorService getExecutor() {
        return executor.access();
    }

    public void collectOutputs(ExecutorRuntimeSupplier gc) {
        for(ServiceDeployment deployment : getDeployment()) {
            deployment.collectOutputs(gc, "JExecutor");
        }
    }
}
