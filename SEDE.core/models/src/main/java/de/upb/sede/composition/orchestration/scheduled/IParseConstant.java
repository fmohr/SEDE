package de.upb.sede.composition.orchestration.scheduled;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IParseConstantNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ParseConstant.Builder.class)
public interface IParseConstant extends ScheduledOperation {

    IParseConstantNode getParseConstantNode();

}

