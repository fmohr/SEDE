package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.WithField;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = AcceptDataNode.Builder.class)
public interface IAcceptDataNode extends BaseNode, WithField, WithMarshalling {

    @Override
    default String getText() {
        return String.format("accept %s", getFieldName());
    }
}
