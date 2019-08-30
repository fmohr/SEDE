package de.upb.sede.util;

public interface Validatable {

    /**
     * Validates the internal structure of this instance.
     *
     * Implementations can throw a custom exception to signal an illegal state.
     */
    void validate() throws RuntimeException;
}
