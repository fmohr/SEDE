package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.IQualifiable;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = Notification.Builder.class)
public interface INotification extends IQualifiable {

    @Value.Default
    @Value.Auxiliary
    default String getDescription() {
        return "Notification " + getQualifier();
    }

}

