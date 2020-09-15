package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.WithField;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FinishNode.Builder.class)
public interface IFinishNode extends BaseNode,
    WithField,
    WithExecutorConnection {

    @Override
    default String getText() {
        return String.format("finish %s", getFieldName());
    }

}
