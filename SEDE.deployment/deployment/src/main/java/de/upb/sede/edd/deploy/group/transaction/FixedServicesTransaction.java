package de.upb.sede.edd.deploy.group.transaction;

import de.upb.sede.edd.deploy.DeploymentWorkflowSettings;

import java.util.ArrayList;
import java.util.List;

public class FixedServicesTransaction {

    private DeploymentWorkflowSettings settings = new DeploymentWorkflowSettings();

    private List<String> services = new ArrayList<>();

    public DeploymentWorkflowSettings getSettings() {
        return settings;
    }

    public void setSettings(DeploymentWorkflowSettings settings) {
        this.settings = settings;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
}
