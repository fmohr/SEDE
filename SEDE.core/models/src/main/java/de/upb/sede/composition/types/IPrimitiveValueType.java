package de.upb.sede.composition.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.core.PrimitiveType;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = PrimitiveValueType.Builder.class)
public interface IPrimitiveValueType extends TypeClass, ValueTypeClass {

    @Override
    @Value.Lazy
    default String getQualifier() {
        return getPrimitiveType().toString();
    }

    PrimitiveType getPrimitiveType();

}
