package de.upb.sede.edd.deploy.specification;

import de.upb.sede.edd.LockableDir;

import java.util.List;

public class ServiceDeployment {

    private DeploymentSpecification specification;

    private LockableDir deployDirectory;

    public ServiceDeployment(DeploymentSpecification specification, LockableDir dir) {
        this.specification = specification;
        this.deployDirectory = dir;
    }

    public void deploy() {
        deployDirectory.getChild("deployment");
    }

    private static class Result {

    }
}
