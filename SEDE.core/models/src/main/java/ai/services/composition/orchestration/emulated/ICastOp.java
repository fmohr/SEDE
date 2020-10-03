package ai.services.composition.orchestration.emulated;

import ai.services.SEDEModelStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.composition.graphs.nodes.IMarshalNode;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CastOp.Builder.class)
public interface ICastOp extends EmulatedOp {

    IMarshalNode getFirstCast();

    @Nullable
    IMarshalNode getSecondCast();



}

