package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

import java.util.Optional;

@SModelStyle
@Value.Immutable
@JsonDeserialize(builder = VariableDesc.Builder.class)
public interface IVariableDesc {

    @Value.Default
    default boolean isMutable(){
        return false;
    }

    Optional<String> getName();

    String getType();
}
