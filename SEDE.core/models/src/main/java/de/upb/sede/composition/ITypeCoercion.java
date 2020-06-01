package de.upb.sede.composition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.core.PrimitiveType;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.Objects;

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
        return getSemanticType() != null && !getSourceType().equals(getResultType());
    }

    @Value.Lazy
    @Deprecated
    public default boolean resultsInSemanticType() {
        // TODO Find a better way to express no to semantic conversion than to abuse the typeCoercion class
        return getSemanticType() != null && getResultType().equals(getSemanticType());
    }

}



