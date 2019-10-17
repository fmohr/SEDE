package de.upb.sede.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = NumericParameter.Builder.class)
public interface INumericParameter extends IParameter{


    @Value.Default
    default boolean isInteger() {
        return false;
    }

    @Nullable
    Double getMin();

    @Nullable
    Double getMax();

    @Nullable
    Integer getRefineSplits();

    @Nullable
    Integer getMinInterval();

    @Nullable
    Double getDefaultValue();
}
