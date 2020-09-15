package ai.services.composition.orchestration.emulated;

import ai.services.SEDEModelStyle;
import ai.services.composition.IMethodResolution;
import ai.services.composition.graphs.nodes.IInstructionNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = InstructionOp.Builder.class)
public interface IInstructionOp extends EmulatedOp {

    IInstructionNode getInstructionNode();

    IMethodResolution getMR();

}

