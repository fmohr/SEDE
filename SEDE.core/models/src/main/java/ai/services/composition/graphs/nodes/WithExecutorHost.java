package de.upb.sede.composition.graphs.nodes;

import de.upb.sede.composition.IIndexedInstruction;

import javax.annotation.Nullable;

public interface WithExecutorHost {

    @Nullable
    String getHostExecutor();

}
