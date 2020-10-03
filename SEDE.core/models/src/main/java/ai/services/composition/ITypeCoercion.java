package ai.services.composition;

import ai.services.core.Primitives;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = TypeCoercion.Builder.class)
public interface ITypeCoercion {

    @Nullable
    String getConstant();

    String getSourceType();

    @Nullable
    String getSemanticType();

    String getResultType();

    @Value.Lazy
    public default boolean hasConstant() {
        return getConstant() != null;
    }

    @Value.Lazy
    public default boolean hasTypeConversion() {
        return !isNullPlug() && getSemanticType() != null && !getSourceType().equals(getResultType());
    }

    @Value.Lazy
    @Deprecated
    public default boolean resultsInSemanticType() {
        // TODO Find a better way to express to semantic conversion than to abuse the typeCoercion class
        return getSemanticType() != null && getResultType().equals(getSemanticType());
    }

    @Value.Lazy
    public default boolean isNullPlug() {
        return getSourceType().equals(Primitives.NULL.name());
    }

    @Value.Lazy
    default boolean isPrimitive() {
        return Primitives.insensitiveValueOf(getSemanticType()).isPresent();
    }
}



