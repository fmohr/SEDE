package ai.services.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import ai.services.exec.auxiliary.DynamicAuxAware;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceParameterizationDesc.Builder.class)
public interface IServiceParameterizationDesc extends DynamicAuxAware {

    List<IParameter> getParameters();

    List<IParameterDependencyDesc> getParameterDependencies();

}
