package de.upb.sede.edd.deploy;


import de.upb.sede.edd.deploy.group.transaction.GroupComponents;
import de.upb.sede.util.Cache;
import de.upb.sede.util.ExtendedByteArrayOutputStream;
import de.upb.sede.util.LazyAccessCache;
import de.upb.sede.util.Streams;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeploymentContext {

    private List<ServiceDeployment> deployments = new ArrayList<>();

    private DeploymentWorkflowSettings settings = new DeploymentWorkflowSettings();

    private Cache<ExecutorService> executor = new LazyAccessCache<>(Executors::newCachedThreadPool);

    private OutputStream outputStream = Streams.safeSystemOut();

    private OutputStream errOut = Streams.safeSystemErr();

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

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public OutputStream getErrOut() {
        return errOut;
    }

    public void collectOutputs(GroupComponents gc) {
        for(ServiceDeployment deployment : getDeployment()) {
            deployment.collectOutputs(gc);
        }
    }
}
