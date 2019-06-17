package de.upb.sede.edd.deploy;

import de.upb.sede.edd.deploy.model.DeploymentMethodStepType;
import de.upb.sede.util.Kneadable;
import de.upb.sede.util.NotKneadableException;

import java.io.File;

public class DeploymentStepRegistry {

    private final static DeploymentStepRegistry instance = new DeploymentStepRegistry();

    public static DeploymentStepRegistry getInstance() {
        return instance;
    }

    private String getStepType(String displayName, Kneadable definition) {
        String type;
        try {
            type = definition.knead(DeploymentMethodStepType.class).getType();
        } catch (NotKneadableException ex) {
            throw new DeploymentException(displayName + " has malformed type field.");
        }
        if(type == null || type.isEmpty()) {
            throw new DeploymentException(displayName + " has no type field.");
        }
        return type;
    }

    public EDDSource toSourceStep(String displayName, File sourceFolder, Kneadable definition) {
        String type = getStepType(displayName, definition);
        switch (type) {
            case GitSourceDeployment.TYPE:
                return new GitSourceDeployment(displayName, sourceFolder, definition);
        }
        throw new DeploymentException(displayName + " source type not recognized: " + type);
    }

    public EDDBuild toBuildStep(String displayName, Kneadable definition) {
        String type = getStepType(displayName, definition);
        switch (type) {
            case GradleProjectBuild.TYPE:
                return new GradleProjectBuild(displayName, definition);
        }
        throw new DeploymentException(displayName + " build type not recognized: " + type);
    }
}
