package ai.services.composition.orchestration.scheduled;

import ai.services.SEDEModelStyle;
import ai.services.composition.graphs.nodes.IAcceptDataNode;
import ai.services.composition.graphs.nodes.ITransmitDataNode;
import ai.services.exec.IExecutorHandle;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = Transmission.Builder.class)
public interface ITransmission extends ScheduledOperation{

    ITransmitDataNode getTransmission();

    IAcceptDataNode getAcceptDataNode();

    IExecutorHandle getSource();

    IExecutorHandle getTarget();

}

