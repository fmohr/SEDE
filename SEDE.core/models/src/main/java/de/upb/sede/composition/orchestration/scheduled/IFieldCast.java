package de.upb.sede.composition.orchestration.scheduled;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IMarshalNode;
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

