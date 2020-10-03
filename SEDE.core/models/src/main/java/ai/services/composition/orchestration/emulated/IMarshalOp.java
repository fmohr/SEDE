package ai.services.composition.orchestration.emulated;

import ai.services.SEDEModelStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.composition.graphs.nodes.IMarshalNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MarshalOp.Builder.class)
public interface IMarshalOp extends EmulatedOp {

    IMarshalNode getMarshalNode();

}

