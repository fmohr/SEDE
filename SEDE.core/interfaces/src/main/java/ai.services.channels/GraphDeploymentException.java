package ai.services.channels;

public class GraphDeploymentException extends RuntimeException {
    public GraphDeploymentException() {
    }

    public GraphDeploymentException(String message) {
        super(message);
    }

    public GraphDeploymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphDeploymentException(Throwable cause) {
        super(cause);
    }

    public GraphDeploymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
