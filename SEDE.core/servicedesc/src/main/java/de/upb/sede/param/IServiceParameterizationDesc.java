package de.upb.sede.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.auxiliary.DynamicAuxAware;
import de.upb.sede.util.DynTypeField;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceParameterizationDesc.Builder.class)
public interface IServiceParameterizationDesc extends DynamicAuxAware {

    List<IParameter> getParameters();

    List<IParameterDependencyDesc> getParameterDependencies();

}
