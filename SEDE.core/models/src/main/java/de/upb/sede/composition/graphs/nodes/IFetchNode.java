package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.WithField;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FetchNode.Builder.class)
public interface IFetchNode extends BaseNode,
    WithField, WithInPlaceCast, WithExecutorConnection {

    @Override
    default String getText() {
        return String.format("fetch %s from %s", getFieldName(), getContactInfo().getQualifier());
    }

}

