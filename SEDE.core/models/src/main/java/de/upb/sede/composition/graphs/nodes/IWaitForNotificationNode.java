package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.WithField;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = NotifyNode.Builder.class)
public interface IWaitForNotificationNode extends BaseNode,
    WithNotification {
    @Override
    default String getText() {
        return String.format("wait for %s", getNotification().getDescription());
    }
}

