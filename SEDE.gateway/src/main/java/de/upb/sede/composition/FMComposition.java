package de.upb.sede.composition;

import java.util.Objects;

/**
 * Composition in FM-Slang notation.
 */
public class FMComposition {
	private final String compositionString;

	public FMComposition(String compositionString) {
		this.compositionString = Objects.requireNonNull(compositionString);
	}

	public String getComposition() {
		return compositionString;
	}

	public FMComposition cloneObject() {
		return new FMComposition(this.getComposition());
	}
}