package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IFieldContainer;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CastTypeNode.Builder.class)
public interface ICastTypeNode extends BaseNode, IFieldContainer {


    String getSourceType();

    String getTargetType();

    boolean castToSemantic();

    String getCasterClasspath();

}
