package de.upb.sede.composition.orchestration.scheduled;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IMarshalNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldMarshal.Builder.class)
public interface IFieldMarshal extends ScheduledOperation {

    IMarshalNode getMarshal();

}

