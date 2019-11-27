package de.upb.sede.composition;

public interface CompStep<I, O> {

    O compose(I input);

}
