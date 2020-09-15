package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = NotifyNode.Builder.class)
public interface INotifyNode extends BaseNode,
    WithExecutorConnection, WithNotification {

    @Override
    default String getText() {
        return String.format("ntf %s with '%s'", getContactInfo().getQualifier(), getNotification().getDescription());
    }

}

