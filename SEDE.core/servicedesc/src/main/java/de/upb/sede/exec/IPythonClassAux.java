package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = PythonClassAux.Builder.class)
public interface IPythonClassAux {
}
