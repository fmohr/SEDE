package de.upb.sede.exec;


import de.upb.sede.IQualifiable;
import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

import java.util.List;

@SModelStyle
@Value.Immutable
public interface IMethodDesc extends IQualifiable {

    List<IVariableDesc> getInputs();

    List<IVariableDesc> getOutputs();

    default boolean isStatic() {
        return false;
    }

}
