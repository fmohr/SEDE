package de.upb.sede.edd.deploy.model;

import de.upb.sede.util.Validatable;

import java.util.Optional;

public class DeploymentSourceDirName implements Validatable {

    private String directoryName = null;

    public Optional<String> getAcr() {
        return Optional.of(directoryName);
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }



    @Override
    public void validate() throws RuntimeException {
        if(directoryName == null)
            throw new IllegalStateException("Local source directory name was not specified.");
    }
}
