package de.upb.sede.edd.deploy;

public class DeploymentException extends RuntimeException {

    public DeploymentException(String message, Exception cause) {
        super(message, cause);
    }

    public DeploymentException(String message) {
        super(message);
    }
}
