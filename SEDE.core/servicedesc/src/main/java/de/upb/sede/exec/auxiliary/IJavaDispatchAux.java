package de.upb.sede.exec.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.Objects;

import static de.upb.sede.exec.IMethodDesc.CONSTRUCTOR_METHOD_NAME;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = JavaDispatchAux.Builder.class)
public interface IJavaDispatchAux {

    @Value.Default
    default boolean staticInvocation() {
        return false;
    }

    @Value.Lazy
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

    @Nullable
    String getMetaclass();

}
