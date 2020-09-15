package ai.services.param;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
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
