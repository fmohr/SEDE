package ai.services.execution.operator;

public class OpException extends RuntimeException {
    public OpException() {
        super();
    }

    public OpException(String message) {
        super(message);
    }

    public OpException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpException(Throwable cause) {
        super(cause);
    }

    protected OpException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
