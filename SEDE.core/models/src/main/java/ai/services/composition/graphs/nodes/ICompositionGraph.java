package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import ai.services.exec.IExecutorHandle;
import org.immutables.value.Value;

import java.util.List;
import java.util.Map;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CompositionGraph.Builder.class)
public interface ICompositionGraph {

    IExecutorHandle getExecutorHandle();

    List<BaseNode> getNodes();

    Map<String, List<Long>> getEdges();

    @Value.Default
    default boolean isClient() {
        return false;
    }

}

