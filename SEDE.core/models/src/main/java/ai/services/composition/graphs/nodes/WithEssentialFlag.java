package ai.services.composition.graphs.nodes;

import org.immutables.value.Value;

public interface WithEssentialFlag {

    @Value.Default
    default boolean isEssential() {
        return false;
    }

}
