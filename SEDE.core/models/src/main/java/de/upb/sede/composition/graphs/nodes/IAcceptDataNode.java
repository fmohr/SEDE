package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IFieldContainer;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = AcceptDataNode.Builder.class)
public interface IAcceptDataNode extends BaseNode, IFieldContainer {

    @Nullable
    ICastTypeNode getCast();

}
