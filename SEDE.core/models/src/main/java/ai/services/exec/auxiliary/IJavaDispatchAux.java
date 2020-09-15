package ai.services.exec.auxiliary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = JavaDispatchAux.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface IJavaDispatchAux {

    String CONSTRUCTOR_METHOD_NAME = "__construct";

    @Nullable
    Boolean staticInvocation();

    @Nullable
    Boolean constructorInvocation();

    @Value.Default
    default int redirectArg() {
        return -1;
    }

    @Nullable
    String methodName();

    @Nullable
    String className();

    @Nullable
    List<String> paramTypes();

}
