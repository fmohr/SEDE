package ai.services.requests.resolve.beta;

import ai.services.composition.graphs.nodes.ICompositionGraph;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = Choreography.Builder.class)
public interface IChoreography {

    List<ICompositionGraph> getCompositionGraph();

    List<String> getReturnFields();

    @Nullable
    String getDotSVG();

}

