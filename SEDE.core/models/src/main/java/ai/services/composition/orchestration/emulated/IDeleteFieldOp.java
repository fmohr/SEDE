package ai.services.composition.orchestration.emulated;

import ai.services.SEDEModelStyle;
import ai.services.composition.graphs.nodes.IDeleteFieldNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DeleteFieldOp.Builder.class)
public interface IDeleteFieldOp extends EmulatedOp {

    IDeleteFieldNode getDeleteFieldNode();

}

