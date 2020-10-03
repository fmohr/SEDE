package ai.services.composition;

import ai.services.composition.graphs.nodes.ICompositionGraph;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DeployRequest.Builder.class)
public interface IDeployRequest extends WithExecutionId {

    ICompositionGraph getCompGraph();

}

