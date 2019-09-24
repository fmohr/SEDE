package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.aux.IJavaParameterizationAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceParameterDesc.Builder.class)
public interface IServiceParameterDesc {

    @Nullable
    IJavaParameterizationAux getJavaParameterizationAuxiliaries();

}
