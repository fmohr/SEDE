package ai.services.composition.graphs.nodes;

import ai.services.composition.types.serialization.IMarshalling;

import javax.annotation.Nullable;

public interface WithMarshalling {

    @Nullable
    IMarshalling getMarshalling();

}
