package ai.services.requests.resolve.beta;

import ai.services.composition.graphs.nodes.ICompositionGraph;
import ai.services.exec.IExecutorContactInfo;
import ai.services.exec.IExecutorHandle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = Choreography.Builder.class)
public interface IChoreography {

    List<ICompositionGraph> getCompositionGraph();

    Map<String, IExecutorContactInfo> getInitialFieldLocation();

    Map<String, IExecutorContactInfo> getReturnFieldLocation();

    @Nullable
    String getDotSVG();

}

