package ai.services.channels;

public class PushNotificationException extends RuntimeException {
    public PushNotificationException() {
    }

    public PushNotificationException(String message) {
        super(message);
    }

    public PushNotificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PushNotificationException(Throwable cause) {
        super(cause);
    }

    public PushNotificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
