package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.WithField;
import de.upb.sede.SEDEModelStyle;
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
