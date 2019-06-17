package de.upb.sede.edd.deploy;

public class DeploymentException extends RuntimeException {

    public DeploymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeploymentException(String message) {
        super(message);
    }

    public DeploymentException(Throwable cause) {
        super(cause);
    }
}
