package de.upb.sede.composition.orchestration.emulated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IMarshalNode;
import de.upb.sede.composition.graphs.nodes.MarshalNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MarshalOp.Builder.class)
public interface IMarshalOp extends EmulatedOp {

    IMarshalNode getMarshalNode();

}

