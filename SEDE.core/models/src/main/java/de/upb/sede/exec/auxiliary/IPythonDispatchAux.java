package de.upb.sede.exec.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = PythonDispatchAux.Builder.class)
public interface IPythonDispatchAux {
}
