package de.upb.sede.edd.deploy;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Mutable;

public class DeploymentWorkflowSettings {

    private boolean waitInQueue = true;
    private boolean update = false;

    public boolean waitInQueue() {
        return waitInQueue;
    }

    public boolean forcedUpdates() {
        return update;
    }

}
