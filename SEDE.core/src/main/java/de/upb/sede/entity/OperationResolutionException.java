package de.upb.sede.entity;

import de.upb.sede.dsl.seco.Operation;

public class OperationResolutionException extends RuntimeException {
	public OperationResolutionException(Operation operation, String message) {
		super(constructFinalErrorMessage(operation,message));
	}
	public OperationResolutionException(Operation operation, String message, Exception ex) {
		super(constructFinalErrorMessage(operation,message), ex);
	}

	static String constructFinalErrorMessage(Operation op, String message) {
		return "Error resolving operation '" + op.toString() + "': " + message;
	}
}
