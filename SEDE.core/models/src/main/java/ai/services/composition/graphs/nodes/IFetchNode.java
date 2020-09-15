package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.WithField;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FetchNode.Builder.class)
public interface IFetchNode extends BaseNode,
    WithField, WithMarshalling, WithExecutorConnection {

    @Override
    default String getText() {
        return String.format("fetch %s from %s", getFieldName(), getContactInfo().getQualifier());
    }

}

