package de.upb.sede.exec.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = JavaMarshalAux.Builder.class)
public interface IJavaMarshalAux {

    @Nullable
    String getMappedJavaClass();

    @Value.Default
    default boolean useCustomHandler() {
        return false;
    }

    @Nullable
    String getHandlerClass();

    @Nullable
    String getHandlerMarshalMethod();

    @Nullable
    String getHandlerUnmarshalMethod();

    @Value.Default
    default boolean useObjectSerialisation() {
        return false;
    }

    @Value.Default
    default boolean useJacksonSerialisation() {
        return false;
    }

    @Nullable
    String jacksonSerialisationClassTarget();

    @Value.Default
    default boolean useLegacyPattern() {
        return false;
    }

}

