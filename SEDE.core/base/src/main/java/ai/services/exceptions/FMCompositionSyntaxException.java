package de.upb.sede.exceptions;

/**
 *
 * @author aminfaez
 *
 *         Exception used in case of a syntax-error in fmCompositions. (Extends
 *         RuntimeException because checked exceptions are bad!)
 */
public class FMCompositionSyntaxException extends RuntimeException {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public FMCompositionSyntaxException(String text, String regex) {
		super(String.format(
				"Parse error in:\n\t%s\nwith regex:\n\t%s.\nVisit https://www.freeformatter.com/java-regex-tester.html to debug the regex if necessary.",
				text, regex));
	}

	public FMCompositionSyntaxException(String message) {
		super(String.format("Error while parsing fm composition string: %s", message));
	}
}
