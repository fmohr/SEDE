package de.upb.sede.util;

public class NotKneadableException extends RuntimeException {
    public NotKneadableException() {

    }

    public NotKneadableException(String message) {
        super(message);
    }

    public NotKneadableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotKneadableException(Throwable cause) {
        super(cause);
    }

    public NotKneadableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
