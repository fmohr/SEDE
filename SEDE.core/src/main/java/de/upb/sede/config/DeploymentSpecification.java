package de.upb.sede.config;

public class DeploymentSpecification extends Configuration {

    /**
     * Reads the configuration files from configPaths and appends them into itself..
     */
    public DeploymentSpecification(String... configPaths) {
        super();
        appendConfigFromFiles(configPaths);
    }


    public boolean canDeploy(String fullClassName) {
        return this.containsKey(fullClassName);
    }

}
