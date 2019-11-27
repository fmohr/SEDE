package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldType.Builder.class)
public interface IFieldType {

    TypeClass getFieldType();

    String getFieldName();

}



