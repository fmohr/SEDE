package de.upb.sede.exec;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.ICommented;
import de.upb.sede.IQualifiable;
import de.upb.sede.SModelStyle;
import de.upb.sede.exec.aux.IJavaReflectionAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MethodDesc.Builder.class)
public interface IMethodDesc extends IQualifiable, ICommented {


    String CONSTRUCTOR_METHOD_NAME = "$construct";

    List<ISignatureDesc> getSignatures();

    @Value.Default
    default boolean isPure() {
        return false;
    }

}
