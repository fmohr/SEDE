package de.upb.sede.edd.deploy;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeploymentContext {

    private List<ServiceDeployment> deployments = new ArrayList<>();

    private DeploymentWorkflowSettings settings = new DeploymentWorkflowSettings();

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
}
