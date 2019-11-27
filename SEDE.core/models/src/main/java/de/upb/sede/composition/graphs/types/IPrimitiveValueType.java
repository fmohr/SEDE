package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.core.PrimitiveType;
import org.immutables.value.Value;

import java.util.Collections;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = PrimitiveValueType.Builder.class)
public interface IPrimitiveValueType extends TypeClass, IValueTypeClass {

    @Override
    @Value.Lazy
    default String getQualifier() {
        return getPrimitiveType().toString();
    }

    @Override
    @Value.Lazy
    default String getSimpleName() {
        return getQualifier();
    }

    @Override
    @Value.Lazy
    default List<String> getMetaTags() {
        return Collections.EMPTY_LIST;
    }


    PrimitiveType getPrimitiveType();

}
