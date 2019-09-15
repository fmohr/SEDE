package de.upb.sede.types.aux;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = JavaTypeAux.Builder.class)
public interface IJavaTypeAux {


}
