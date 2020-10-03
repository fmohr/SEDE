package ai.services.beta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import ai.services.exec.IExecutorHandle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorRegistration.Builder.class)
public interface IExecutorRegistration {

    IExecutorHandle getExecutorHandle();

}
