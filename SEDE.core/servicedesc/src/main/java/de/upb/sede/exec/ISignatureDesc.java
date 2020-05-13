package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.CommentAware;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.auxiliary.DynamicAuxAware;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = SignatureDesc.Builder.class)
public interface ISignatureDesc extends CommentAware, DynamicAuxAware {

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
