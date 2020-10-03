package ai.services.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = NopNode.Builder.class)
public interface INopNode extends BaseNode {
    @Override
    default String getText() {
        return String.format("nop");
    }

}

