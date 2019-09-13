package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.ICommented;
import de.upb.sede.SModelStyle;
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
    IJavaMethodAux getJavaMethodAux();

}
