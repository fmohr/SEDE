package ai.services.exec;

import ai.services.composition.graphs.nodes.BaseNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutionError.Builder.class)
public interface IExecutionError {

    @Nullable
    BaseNode getErroredNode();

    String getMessage();

    @Nullable
    @Value.Auxiliary
    String getStacktrace();

}

