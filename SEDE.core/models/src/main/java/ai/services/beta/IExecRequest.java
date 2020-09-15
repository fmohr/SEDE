package ai.services.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecRequest.Builder.class)
public interface IExecRequest extends IQualifiableRequest {

    // TODO create a graph type and change the return type to it
    String getCompositionGraph();

}
