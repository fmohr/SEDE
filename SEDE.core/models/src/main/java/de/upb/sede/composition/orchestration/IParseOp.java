package de.upb.sede.composition.orchestration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IParseConstantNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ParseOp.Builder.class)
public interface IParseOp extends EmulatedOp {

    IParseConstantNode getParseConstantNode();

}

