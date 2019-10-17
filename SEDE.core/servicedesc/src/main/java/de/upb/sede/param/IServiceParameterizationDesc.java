package de.upb.sede.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import de.upb.sede.param.auxiliary.IJavaParameterizationAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceParameterizationDesc.Builder.class)
public interface IServiceParameterizationDesc {

    List<IParameter> getParameters();

    List<IParameterDependencyDesc> getParameterDependencies();

    @Nullable
    IJavaParameterizationAux getJavaParameterizationAuxiliaries();

}
