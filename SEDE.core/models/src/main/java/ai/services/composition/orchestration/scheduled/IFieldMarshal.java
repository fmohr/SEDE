package ai.services.composition.orchestration.scheduled;

import ai.services.SEDEModelStyle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.composition.graphs.nodes.IMarshalNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldMarshal.Builder.class)
public interface IFieldMarshal extends ScheduledOperation {

    IMarshalNode getMarshal();

}

