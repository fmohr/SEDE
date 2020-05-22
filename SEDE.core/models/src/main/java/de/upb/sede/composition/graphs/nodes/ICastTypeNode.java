package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.WithField;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CastTypeNode.Builder.class)
public interface ICastTypeNode extends BaseNode, WithField {

    String getSourceType();

    String getTargetType();

    boolean castToSemantic();

    String getCasterClasspath();

}
