package de.upb.sede.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = InterfaceParameter.Builder.class)
public interface IInterfaceParameter extends IParameter {


    default Object getDefaultValue() {
        return null;
    }

    String getInterfaceQualifier();

}



