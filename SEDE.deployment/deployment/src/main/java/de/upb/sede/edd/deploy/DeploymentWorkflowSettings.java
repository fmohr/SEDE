package de.upb.sede.edd.deploy;

public class DeploymentWorkflowSettings {

    private boolean waitInQueue = true;

    private boolean update = false;

    private boolean incurIncludedServices = false;

    public boolean waitInQueue() {
        return waitInQueue;
    }

    public boolean forcedUpdates() {
        return update;
    }

    public void setWaitInQueue(boolean waitInQueue) {
        this.waitInQueue = waitInQueue;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isIncurIncludedServices() {
        return incurIncludedServices;
    }

    public void setIncurIncludedServices(boolean incurIncludedServices) {
        this.incurIncludedServices = incurIncludedServices;
    }

    public boolean isWaitInQueue() {
        return waitInQueue;
    }

    public boolean isUpdate() {
        return update;
    }
}
