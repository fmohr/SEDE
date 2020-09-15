package ai.services.composition.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DataValueType.Builder.class)
public interface IDataValueType extends TypeClass, ValueTypeClass {


}

