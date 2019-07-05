package de.upb.sede.edd.deploy.model;

import de.upb.sede.util.Validatable;

public class  DeploymentMethodStepType implements Validatable {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void validate() throws RuntimeException {
        if(type == null || type.isEmpty()) {
            throw new IllegalStateException("Not type was specified.");
        }
    }
}
