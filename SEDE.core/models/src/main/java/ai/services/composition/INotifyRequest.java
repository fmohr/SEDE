package ai.services.composition;

import ai.services.composition.graphs.nodes.INotification;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = NotifyRequest.Builder.class)
public interface INotifyRequest extends INotification, WithExecutionId {

    @Value.Default
    default boolean isSuccessfulNotification() {
        return true;
    }
}

