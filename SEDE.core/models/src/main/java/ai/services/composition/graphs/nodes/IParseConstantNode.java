package ai.services.composition.graphs.nodes;

import ai.services.core.Primitives;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ParseConstantNode.Builder.class)
public interface IParseConstantNode extends BaseNode {

    String getConstantValue();

    Primitives getConstantType();

    @Override
    default String getText() {
        return String.format("parse %s to %s", getConstantValue(), getConstantType());
    }
}
