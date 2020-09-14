package de.upb.sede.exec.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = StdTypeAux.Builder.class)
public interface IStdTypeAux {

    @Nullable
    IJavaMarshalAux getJavaMarshalAux();

}
