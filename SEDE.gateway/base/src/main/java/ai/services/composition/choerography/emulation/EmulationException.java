package ai.services.composition.choerography.emulation;

public class EmulationException extends Exception {

    public EmulationException() {
    }

    public EmulationException(String message) {
        super(message);
    }

    public EmulationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmulationException(Throwable cause) {
        super(cause);
    }

}
