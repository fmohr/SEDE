package de.upb.sede.composition.orchestration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.*;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = TransmissionOp.Builder.class)
public interface ITransmissionOp extends EmulatedOp {

    INotifyNode getSourceReadyNtf();

    IWaitForNotificationNode getTargetReadyNtf();

    WaitForNotificationNode getTargetReceivedNtf();

    ITransmitDataNode getTransmitDataNode();

    @Nullable
    IDeleteFieldNode getDeleteFieldNode();

}

