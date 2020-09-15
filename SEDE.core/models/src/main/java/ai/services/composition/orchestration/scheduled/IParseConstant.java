package ai.services.composition.orchestration.scheduled;

import ai.services.SEDEModelStyle;
import ai.services.composition.graphs.nodes.IParseConstantNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ParseConstant.Builder.class)
public interface IParseConstant extends ScheduledOperation {

    IParseConstantNode getParseConstantNode();

}

