package de.upb.sede.composition.graphs.nodes;

public abstract class BaseNode {

	public BaseNode() {

	}

	/**
	 * Returns true if the object has the same pointer. Signed final so derived
	 * classes can't override the functionality of equals.
	 */
	public final boolean equals(Object otherObject) {
		return super.equals(otherObject);
	}

	/**
	 * Signed final so derived classes can't override the functionality of hashCode.
	 */
	public final int hashCode() {
		return super.hashCode();
	}
}
