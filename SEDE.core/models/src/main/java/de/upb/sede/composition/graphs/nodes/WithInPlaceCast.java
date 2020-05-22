package de.upb.sede.composition.graphs.nodes;

import javax.annotation.Nullable;

public interface WithInPlaceCast {

    @Nullable
    ICastTypeNode inPlaceCast();

}
