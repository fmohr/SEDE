package de.upb.sede.edd.deploy.specification;

import de.upb.sede.edd.LockableDir;

public class DeploymentResult {
    private LockableDir dir;

    public DeploymentResult(LockableDir dir) {
        this.dir = dir;
    }


}
