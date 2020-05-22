package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.IExecutorContactInfo;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = SendGraphNode.Builder.class)
public interface ISendGraphNode extends BaseNode, WithExecutorConnection {

    String getGraph();

}
