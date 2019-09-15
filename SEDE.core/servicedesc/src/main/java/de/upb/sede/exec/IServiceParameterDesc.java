package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import de.upb.sede.exec.aux.IJavaParameterizationAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceParameterDesc.Builder.class)
public interface IServiceParameterDesc {

    @Nullable
    IJavaParameterizationAux getJavaParameterizationAuxiliaries();

}
