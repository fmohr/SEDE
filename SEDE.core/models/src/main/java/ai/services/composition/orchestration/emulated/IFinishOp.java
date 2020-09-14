package de.upb.sede.composition.orchestration.emulated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.INotifyNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FinishOp.Builder.class)
public interface IFinishOp extends EmulatedOp{

    INotifyNode getExecutionFinishedNtf();

}

