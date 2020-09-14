package de.upb.sede.composition.graphs.nodes;

import de.upb.sede.composition.types.serialization.IMarshalling;

import javax.annotation.Nullable;

public interface WithMarshalling {

    @Nullable
    IMarshalling getMarshalling();

}
