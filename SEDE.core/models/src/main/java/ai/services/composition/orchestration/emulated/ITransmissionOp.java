package ai.services.composition.orchestration.emulated;

import ai.services.SEDEModelStyle;
import ai.services.composition.graphs.nodes.IDeleteFieldNode;
import ai.services.composition.graphs.nodes.INotifyNode;
import ai.services.composition.graphs.nodes.ITransmitDataNode;
import ai.services.composition.graphs.nodes.IWaitForNotificationNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = TransmissionOp.Builder.class)
public interface ITransmissionOp extends EmulatedOp {

    INotifyNode getSourceReadyNtf();

    IWaitForNotificationNode getTargetReadyNtf();

    IWaitForNotificationNode getTargetReceivedNtf();

    ITransmitDataNode getTransmitDataNode();

    @Nullable
    IDeleteFieldNode getDeleteFieldNode();

}

