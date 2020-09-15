package ai.services.composition.orchestration.emulated;

import ai.services.SEDEModelStyle;
import ai.services.composition.graphs.nodes.INopNode;
import ai.services.composition.graphs.nodes.IWaitForNotificationNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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

