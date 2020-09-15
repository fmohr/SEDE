package ai.services.composition.graphs.nodes;

import ai.services.SEDEModelStyle;
import ai.services.WithField;
import ai.services.composition.types.serialization.IMarshalling;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.annotation.Nonnull;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MarshalNode.Builder.class)
public interface IMarshalNode extends BaseNode, WithField, WithMarshalling {

    @Nonnull
    IMarshalling getMarshalling();

    @Override
    default String getText() {
        return String.format("marshal %s '%s' %s '%s'", getFieldName(), getMarshalling().getValueType().getTypeQualifier(),
            getMarshalling().getDirection() == IMarshalling.Direction.MARSHAL? "to":"from",
            getMarshalling().getSemanticName());
    }
}
