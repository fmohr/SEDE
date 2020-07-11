package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.WithField;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.IExecutorContactInfo;
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
