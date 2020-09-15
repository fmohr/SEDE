package ai.services.composition.orchestration.scheduled;

import ai.services.SEDEModelStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.composition.graphs.nodes.IMarshalNode;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldCast.Builder.class)
public interface IFieldCast extends ScheduledOperation {

    IMarshalNode getFirstCast();

    @Nullable
    IMarshalNode getSecondCast();

}

