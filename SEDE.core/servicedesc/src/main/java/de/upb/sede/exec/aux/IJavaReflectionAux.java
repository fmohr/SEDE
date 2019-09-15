package de.upb.sede.exec.aux;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import de.upb.sede.exec.IServiceDesc;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;

import static de.upb.sede.exec.IMethodDesc.CONSTRUCTOR_METHOD_NAME;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = JavaReflectionAux.Builder.class)
public interface IJavaReflectionAux {

    @Value.Default
    default boolean staticInvocation() {
        return false;
    }

    default boolean constructorInvocation() {
        return Objects.equals(methodName(), CONSTRUCTOR_METHOD_NAME);
    }

//    Map<String, String> returnArgs(); // TODO map inputs to outputs instead of the index method:

    @Value.Default
    default int redirectArg() {
        return -1;
    }

    @Nullable
    String methodName();

    @Nullable
    String className();

}
