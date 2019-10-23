package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.ConstructReference;
import de.upb.sede.IQualifiable;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = MethodRef.Builder.class)
public interface IMethodRef extends ConstructReference {

    IServiceRef getServiceRef();

}
