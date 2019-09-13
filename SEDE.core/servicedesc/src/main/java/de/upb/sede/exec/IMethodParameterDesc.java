package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MethodParameterDesc.Builder.class)
public interface IMethodParameterDesc {

    @Value.Default
    default boolean isMutable(){
        return false;
    }

    Optional<String> getName();

    String getType();

    @Nullable
    String getFixedValue();
}
