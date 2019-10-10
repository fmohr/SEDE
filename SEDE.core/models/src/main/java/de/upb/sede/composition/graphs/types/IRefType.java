package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IQualifiable;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = RefType.Builder.class)
public interface IRefType extends TypeClass {

    TypeClass getReferencedType();

}
