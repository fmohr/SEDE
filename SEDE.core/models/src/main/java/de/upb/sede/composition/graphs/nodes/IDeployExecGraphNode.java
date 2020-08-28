package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DeployExecGraphNode.Builder.class)
public interface IDeployExecGraphNode extends BaseNode, WithExecutorConnection {

    String getGraph();

    @Override
    default String getText() {
        return String.format("send graph to %s", getContactInfo().getQualifier());
    }
}
