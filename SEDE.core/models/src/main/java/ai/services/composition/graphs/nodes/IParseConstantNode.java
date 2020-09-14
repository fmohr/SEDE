package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.core.PrimitiveType;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ParseConstantNode.Builder.class)
public interface IParseConstantNode extends BaseNode {

    String getConstantValue();

    PrimitiveType getConstantType();

    @Override
    default String getText() {
        return String.format("parse %s to %s", getConstantValue(), getConstantType());
    }
}
