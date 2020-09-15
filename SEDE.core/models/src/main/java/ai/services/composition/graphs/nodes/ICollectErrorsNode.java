package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.WithField;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CollectErrorsNode.Builder.class)
public interface ICollectErrorsNode extends BaseNode, WithField {

    String EXECUTION_ERRORS_FIELDNAME = "__execution_errors_%s";

    List<String> getErrorFields();

    @Override
    default String getText() {
        return String.format("Collect errors into %s", getFieldName());
    }
}

