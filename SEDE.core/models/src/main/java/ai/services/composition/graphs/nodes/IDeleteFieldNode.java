package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.WithField;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

/**
 *
 */
@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DeleteFieldNode.Builder.class)
public interface IDeleteFieldNode extends BaseNode, WithField {

    @Override
    default String getText() {
        return String.format("delete %s", getFieldName());
    }
}
