package ai.services.exceptions;

public class ExecutionIdOccupiedException extends RuntimeException {
	public ExecutionIdOccupiedException(String requestId){
		super("Cannot run another execution with occupied requestId: \"" + requestId + "\".");
	}
}
