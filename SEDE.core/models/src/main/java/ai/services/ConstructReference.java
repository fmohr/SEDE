package de.upb.sede;

import de.upb.sede.IQualifiable;

/**
 * References a declared construct.
 * This is not to be confused with a value reference.
 */
public interface ConstructReference {

    IQualifiable getRef();

}
