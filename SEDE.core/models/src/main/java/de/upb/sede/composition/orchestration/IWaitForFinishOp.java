package de.upb.sede.composition.orchestration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.INopNode;
import de.upb.sede.composition.graphs.nodes.IWaitForNotificationNode;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = WaitForFinishOp.Builder.class)
public interface IWaitForFinishOp extends EmulatedOp {

    List<IWaitForNotificationNode> getExFinishedNtf();

    INopNode getNopNode();

}

