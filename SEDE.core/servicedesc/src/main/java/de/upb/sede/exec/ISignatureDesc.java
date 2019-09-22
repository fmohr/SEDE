package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.ICommented;
import de.upb.sede.SModelStyle;
import de.upb.sede.exec.aux.IJavaDispatchAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = SignatureDesc.Builder.class)
public interface ISignatureDesc extends ICommented {

    List<IMethodParameterDesc> getInputs();

    List<IMethodParameterDesc> getOutputs();

    @Nullable
    IJavaDispatchAux getJavaAux();

    @Value.Default
    default boolean isPure() {
        return false;
    }

    @Value.Default
    default boolean isContextFree() {
        return false;
    }
}
