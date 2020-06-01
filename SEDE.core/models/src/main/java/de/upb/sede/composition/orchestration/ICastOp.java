package de.upb.sede.composition.orchestration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.ICastTypeNode;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CastOp.Builder.class)
public interface ICastOp extends EmulatedOp {

    ICastTypeNode getFirstCast();

    @Nullable
    ICastTypeNode getSecondCast();



}

