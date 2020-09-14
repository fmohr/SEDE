package de.upb.sede.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = CategoryParameter.Builder.class)
public interface ICategoryParameter extends IParameter {

    @Nullable
    String getDefaultValue();

    List<String> getCategories();

}
