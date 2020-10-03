package ai.services.composition.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceInstanceType.Builder.class)
public interface IServiceInstanceType extends TypeClass, ValueTypeClass {

}
