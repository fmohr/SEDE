package de.upb.sede.exec;

import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

import java.util.Optional;

@SModelStyle
@Value.Immutable
public interface IVariableDesc {

    default boolean isMutable(){
        return false;
    }

    Optional<String> getName();

    String getType();


}
