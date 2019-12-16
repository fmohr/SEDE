package de.upb.sede.composition;

public interface CompileStep<I, O> {

    O step(I input);

}
