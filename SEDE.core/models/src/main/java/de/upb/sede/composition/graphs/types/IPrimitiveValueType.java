package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = PrimitiveValueType.Builder.class)
public interface IPrimitiveValueType extends TypeClass {

    enum PrimitiveTypes {
        Number, String, Bool, Null
    }

}
