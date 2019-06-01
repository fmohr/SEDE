package de.upb.sede.edd.deploy.specification;

import de.upb.sede.edd.deploy.AcrPath;

public class DeploymentSourceDirAcr implements DeploymentSource {

    private AcrPath directoryAcronym;

    public AcrPath getDirectoryAcronym() {
        return directoryAcronym;
    }

    public void setDirectoryAcronym(AcrPath directoryAcronym) {
        this.directoryAcronym = directoryAcronym;
    }

}
