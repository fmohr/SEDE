package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.WithField;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = InterruptExecNode.Builder.class)
public interface IInterruptExecNode  extends BaseNode, WithField, WithExecutorConnection {

    @Override
    default String getText() {
        return String.format("send interrupt %s to %s", getFieldName(), getContactInfo().getQualifier());
    }

}
