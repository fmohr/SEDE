package de.upb.sede.edd.deploy.model;

import de.upb.sede.util.OptionalField;
import de.upb.sede.util.Validatable;

import java.util.Optional;

public class DeploymentSourceDirAcr {

    private String directoryAcronym = null;

    public Optional<String> getAcr() {
        return Optional.of(directoryAcronym);
    }

    public String getDirectoryAcronym() {
        return directoryAcronym;
    }

    public void setDirectoryAcronym(String directoryAcronym) {
        this.directoryAcronym = directoryAcronym;
    }
}
