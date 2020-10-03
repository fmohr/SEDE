package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.WithField;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = TransmitDataNode.Builder.class)
public interface ITransmitDataNode extends BaseNode,
    WithField, WithExecutorConnection, WithMarshalling {

    @Override
    default String getText() {
        return String.format("transmit %s to %s", getFieldName(), getContactInfo().getQualifier());
    }
}
