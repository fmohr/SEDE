package ai.services.exec;

import ai.services.param.IServiceParameterizationDesc;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.CommentAware;
import ai.services.QualifierDefinition;
import ai.services.SEDEModelStyle;
import ai.services.exec.auxiliary.DynamicAuxAware;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceDesc.Builder.class)
public interface IServiceDesc extends QualifierDefinition, CommentAware, DynamicAuxAware {

    public static final String STATE_FIELD = "state";

    List<IMethodDesc> getMethods();

    List<String> getInterfaces();

    @Value.Default
    default boolean isAbstract() {
        return false;
    }

    Map<String, String> getFieldTypes();

    @Nullable
    IServiceParameterizationDesc getServiceParameters();

    @Nullable
    @Value.Auxiliary
    default String getStateType() {
        return getFieldTypes().getOrDefault(STATE_FIELD, null);
    }

}


