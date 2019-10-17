package de.upb.sede.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.ICommented;
import de.upb.sede.IQualifiable;
import de.upb.sede.SModelStyle;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
import de.upb.sede.types.auxiliary.IJavaTypeAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = DataTypeDesc.Builder.class)
public interface IDataTypeDesc extends IQualifiable, ICommented {

    String getSemanticType();

    @Nullable
    IJavaTypeAux getJavaTypeAux();

}
