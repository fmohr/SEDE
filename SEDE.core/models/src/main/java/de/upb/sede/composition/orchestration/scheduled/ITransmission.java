package de.upb.sede.composition.orchestration.scheduled;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IAcceptDataNode;
import de.upb.sede.composition.graphs.nodes.ITransmitDataNode;
import de.upb.sede.exec.IExecutorHandle;
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

