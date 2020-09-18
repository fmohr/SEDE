package ai.services.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MethodParameterDesc.Builder.class)
public interface IMethodParameterDesc {

    String getType();

    @Nullable
    String getName();

    @Nullable
    String getFixedValue();

    @Value.Default
    default boolean callByValue(){
        return true;
    }

    @Value.Default
    default boolean readOnly() {
        return false;
    }

}