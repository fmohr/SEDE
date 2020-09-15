package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = WaitForNotificationNode.Builder.class)
public interface IWaitForNotificationNode extends BaseNode,
    WithNotification {

    @Override
    default String getText() {
        return String.format("wait for %s", getNotification().getDescription());
    }

}

