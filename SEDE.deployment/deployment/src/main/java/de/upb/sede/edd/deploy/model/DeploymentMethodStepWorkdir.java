package de.upb.sede.edd.deploy.model;

import de.upb.sede.edd.deploy.AcrPath;
import de.upb.sede.util.Validatable;

public class DeploymentMethodStepWorkdir implements Validatable {

    private AcrPath workingDir;

    public AcrPath getWorkingDir() {
        return workingDir;
    }

    @Override
    public void validate() throws RuntimeException {
        if(workingDir == null) {
            throw new IllegalStateException("No working directory was specified.");
        }
    }
}
