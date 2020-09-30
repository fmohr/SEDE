package ai.services.composition.orchestration.emulated;

import ai.services.SEDEModelStyle;
import ai.services.composition.graphs.nodes.IAcceptDataNode;
import ai.services.composition.graphs.nodes.IDeleteFieldNode;
import ai.services.composition.graphs.nodes.INotifyNode;
import ai.services.composition.graphs.nodes.IWaitForNotificationNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = AcceptOp.Builder.class)
public interface IAcceptOp extends EmulatedOp {

    IWaitForNotificationNode getSourceReadyNtf();

    INotifyNode getTargetReadyNtf();

    INotifyNode getTargetReceivedNtf();

    IAcceptDataNode getAcceptDataNode();
    @Nullable
    IDeleteFieldNode getDeleteBeforeAccept();
}

