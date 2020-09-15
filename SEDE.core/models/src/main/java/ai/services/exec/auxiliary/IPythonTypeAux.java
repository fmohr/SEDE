package ai.services.exec.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = PythonTypeAux.Builder.class)
public interface IPythonTypeAux {
}
