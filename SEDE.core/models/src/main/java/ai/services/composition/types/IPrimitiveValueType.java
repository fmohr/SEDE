package ai.services.composition.types;

import ai.services.core.Primitives;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = PrimitiveValueType.Builder.class)
public interface IPrimitiveValueType extends TypeClass, ValueTypeClass {

    @Override
    @Value.Lazy
    default String getTypeQualifier() {
        return getPrimitiveType().toString();
    }

    Primitives getPrimitiveType();

}
