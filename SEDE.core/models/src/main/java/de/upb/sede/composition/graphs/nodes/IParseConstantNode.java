package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ParseConstantNode.Builder.class)
public interface IParseConstantNode extends BaseNode {

    String getConstantValue();

    @Nullable
    ConstantType getConstantType();

    enum ConstantType {
        NULL, String, Number, Bool;
    }

}
