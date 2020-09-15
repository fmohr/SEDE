package ai.services.composition.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import ai.services.core.PrimitiveType;
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

    PrimitiveType getPrimitiveType();

}
