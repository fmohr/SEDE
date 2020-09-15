package ai.services.composition;

import ai.services.composition.types.TypeClass;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = FieldType.Builder.class)
public interface IFieldType {

    public String getFieldname();

    public TypeClass getType();

}



