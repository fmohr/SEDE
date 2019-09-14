package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = JavaMethodAux.Builder.class)
public interface IJavaMethodAux {

    @Value.Default
    default boolean staticInvocation() {
        return false;
    }

    @Value.Default
    default int redirectArg() {
        return -1;
    }

}
