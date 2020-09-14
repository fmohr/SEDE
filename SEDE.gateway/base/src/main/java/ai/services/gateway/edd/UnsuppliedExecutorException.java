package ai.services.gateway.edd;

public class UnsuppliedExecutorException extends RuntimeException {

    public UnsuppliedExecutorException() {
        super();
    }

    public UnsuppliedExecutorException(String message) {
        super(message);
    }

    public UnsuppliedExecutorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsuppliedExecutorException(Throwable cause) {
        super(cause);
    }

}
