package de.upb.sede.composition.graphs.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.IQualifiable;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import java.lang.reflect.Type;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceInstanceType.Builder.class)
public interface IServiceInstanceType extends TypeClass, IValueTypeClass {
}
