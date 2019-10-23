package de.upb.sede.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecRequest.Builder.class)
public interface IExecRequest extends IQualifiableRequest {

    // TODO create a graph type and change the return type to it
    String getCompositionGraph();

}
