package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import de.upb.sede.exec.aux.IJavaParameterizationAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceParameterizationDesc.Builder.class)
public interface IServiceParameterizationDesc {

    List<IServiceParameterDesc> getServiceParameterDesc();

    @Nullable
    IJavaParameterizationAux getJavaParameterizationAuxiliaries();
}
