package de.upb.sede.types.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = JavaTypeAux.Builder.class)
public interface IJavaTypeAux {

    @Nullable
    IJavaDispatchAux getDataCastHandler();

    @Nullable
    String getClassName();

}
