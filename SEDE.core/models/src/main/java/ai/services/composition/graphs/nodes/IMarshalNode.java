package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.WithField;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.types.serialization.IMarshalling;
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
