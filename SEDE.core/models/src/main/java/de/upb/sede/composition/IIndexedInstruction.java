package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IInstructionNode;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = IndexedInstruction.Builder.class)
public interface IIndexedInstruction {

    IInstructionNode getInstruction();

    @Value.Derived
    default Long getIndex() {
        return getInstruction().getIndex().get();
    }

}



