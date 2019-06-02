package de.upb.sede.edd.deploy.model;

import de.upb.sede.util.OptionalField;

import java.util.Optional;

public class DeploymentSourceDirAcr implements DeploymentSource {

    private OptionalField<String> directoryAcronym;

    public Optional<String> getAcr() {
        return directoryAcronym.opt();
    }

}
