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
        return String.format("%s %s - %s %s %s",
            getMarshalling().getDirection().isMarshal()? "marshal" : "unmarshal",
            getFieldName(),
            getMarshalling().getValueType().getTypeQualifier(),
            getMarshalling().getDirection().isMarshal() ? "->":"<-",
            getMarshalling().getSemanticName());
    }

    @Value.Default
    default boolean isEssential() {
        return true;
    }
}
