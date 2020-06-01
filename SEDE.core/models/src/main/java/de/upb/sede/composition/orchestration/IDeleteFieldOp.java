package de.upb.sede.composition.orchestration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IDeleteFieldNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DeleteFieldOp.Builder.class)
public interface IDeleteFieldOp extends EmulatedOp {

    IDeleteFieldNode getDeleteFieldNode();

}

