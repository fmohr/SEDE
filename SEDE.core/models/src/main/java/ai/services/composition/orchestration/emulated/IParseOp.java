package ai.services.composition.orchestration.emulated;

import ai.services.SEDEModelStyle;
import ai.services.composition.graphs.nodes.IParseConstantNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ParseOp.Builder.class)
public interface IParseOp extends EmulatedOp {

    IParseConstantNode getParseConstantNode();

}

