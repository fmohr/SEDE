package de.upb.sede;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldAccess.Builder.class)
public interface IFieldAccess extends IFieldContainer{

    @Value.Default
    default boolean isDereference() {
        return false;
    }

    @Nullable
    IFieldAccess getMember();

}
