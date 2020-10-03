package ai.services.composition;

import ai.services.composition.graphs.nodes.IInstructionNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = IndexedInstruction.Builder.class)
public interface IIndexedInstruction {

    IInstructionNode getInstruction();

    @Value.Derived
    default Long getIndex() {
        return getInstruction().getIndex();
    }

}



