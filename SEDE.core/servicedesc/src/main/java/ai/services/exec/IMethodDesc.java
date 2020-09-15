package ai.services.exec;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.CommentAware;
import ai.services.QualifierDefinition;
import ai.services.SEDEModelStyle;
import ai.services.exec.auxiliary.DynamicAuxAware;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MethodDesc.Builder.class)
public interface IMethodDesc extends QualifierDefinition, CommentAware, DynamicAuxAware {

    String CONSTRUCTOR_METHOD_NAME = "__construct";

    List<IMethodParameterDesc> getInputs();

    List<IMethodParameterDesc> getOutputs();

    @Value.Default
    default boolean isPure() {
        return false;
    }

    @Value.Default
    default boolean isContextFree() {
        return false;
    }

}
