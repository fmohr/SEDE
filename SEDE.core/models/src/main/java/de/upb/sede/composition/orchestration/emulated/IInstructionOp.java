package de.upb.sede.composition.orchestration.emulated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.IMethodResolution;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = InstructionOp.Builder.class)
public interface IInstructionOp extends EmulatedOp {

    IInstructionNode getInstructionNode();

    IMethodResolution getMR();

}

