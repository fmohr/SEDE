package de.upb.sede.exceptions;

public class CompositionSemanticException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CompositionSemanticException(String message) {
		super(String.format("Semantic error in composition:\n\t%s", message));
	}

}
