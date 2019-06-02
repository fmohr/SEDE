package de.upb.sede.edd.deploy.model;

public class DeploymentReport {

    private boolean deployed = false;

    private long timestamp;

    public boolean isDeployed() {
        return deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
