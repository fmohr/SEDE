package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IFieldContainer;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

/**
 *
 */
@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DeleteFieldNode.Builder.class)
public interface IDeleteFieldNode extends BaseNode, IFieldContainer {

}