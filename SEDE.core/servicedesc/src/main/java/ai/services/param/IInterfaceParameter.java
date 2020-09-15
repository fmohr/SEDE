package ai.services.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

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



