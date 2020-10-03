package ai.services.composition;

import ai.services.composition.graphs.nodes.INotification;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = NtfInstance.Builder.class)
public interface INtfInstance extends INotification, WithExecutionId {

    @Value.Default
    default boolean isSuccessfulNotification() {
        return true;
    }
}

