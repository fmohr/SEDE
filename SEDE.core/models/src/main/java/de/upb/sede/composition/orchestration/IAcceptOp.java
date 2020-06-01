package de.upb.sede.composition.orchestration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.*;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = AcceptOp.Builder.class)
public interface IAcceptOp extends EmulatedOp {

    IWaitForNotificationNode getSourceReadyNtf();

    INotifyNode getTargetReadyNtf();

    INotifyNode getTargetReceivedNtf();

    IAcceptDataNode getAcceptDataNode();

}

