package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.WithField;
import de.upb.sede.SEDEModelStyle;
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
