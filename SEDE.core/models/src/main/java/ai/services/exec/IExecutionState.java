package ai.services.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutionState.Builder.class)
public interface IExecutionState {

    @Value.Default
    default boolean isDeployed() {
        return false;
    }

    @Value.Default
    default boolean isStarted() {
        return false;
    }

    @Value.Default
    default boolean isFinished() {
        return false;
    }

}

