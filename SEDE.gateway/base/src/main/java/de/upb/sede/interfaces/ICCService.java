package de.upb.sede.interfaces;

import de.upb.sede.composition.ICCRequest;
import de.upb.sede.composition.ICompositionCompilation;

public interface ICCService {

    /**
     * Accepts a ICCRequest and parses the included fmcomposition and performs a static code analysis.
     * The returned static compilation can be used for execution.
     * The series of actions performed by this method and its result is called CC short for `composition compilation`.
     *
     * @param ccRequest the composition compilation request.
     * @return the resulting compilation from static code analysis of the composition
     */
    public ICompositionCompilation compileComposition(ICCRequest ccRequest);

}
