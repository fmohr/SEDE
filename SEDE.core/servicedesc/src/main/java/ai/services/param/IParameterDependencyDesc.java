package ai.services.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ParameterDependencyDesc.Builder.class)
public interface IParameterDependencyDesc {

    String getPremise();

    String getConclusion();

}
