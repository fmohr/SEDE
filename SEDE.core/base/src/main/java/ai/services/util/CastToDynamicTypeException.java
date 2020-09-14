package de.upb.sede.util;

public class CastToDynamicTypeException extends RuntimeException {
    public CastToDynamicTypeException() {

    }

    public CastToDynamicTypeException(String message) {
        super(message);
    }

    public CastToDynamicTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CastToDynamicTypeException(Throwable cause) {
        super(cause);
    }

    public CastToDynamicTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
