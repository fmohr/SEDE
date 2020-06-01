package de.upb.sede.composition.choerography.emulation;

public class OrchestrationException extends RuntimeException{

    public OrchestrationException() {
    }

    public OrchestrationException(String message) {
        super(message);
    }

    public OrchestrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrchestrationException(Throwable cause) {
        super(cause);
    }

    public OrchestrationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public static OrchestrationException notFoundAsInitialField(String field) {
        return new OrchestrationException(String.format("The field '%s' was expected to be an initial field.", field));
    }
}
