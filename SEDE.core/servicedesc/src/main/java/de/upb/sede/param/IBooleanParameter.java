package de.upb.sede.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = BooleanParameter.Builder.class)
public interface IBooleanParameter extends IParameter{


    @Nullable
    Boolean getDefaultValue();

}
