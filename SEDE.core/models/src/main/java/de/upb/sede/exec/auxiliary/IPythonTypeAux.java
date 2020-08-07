package de.upb.sede.exec.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = PythonTypeAux.Builder.class)
public interface IPythonTypeAux {
}
