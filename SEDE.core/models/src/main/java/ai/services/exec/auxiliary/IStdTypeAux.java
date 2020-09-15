package ai.services.exec.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
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
