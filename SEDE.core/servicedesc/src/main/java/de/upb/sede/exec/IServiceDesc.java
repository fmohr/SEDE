package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.CommentAware;
import de.upb.sede.IQualifiable;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.auxiliary.DynamicAuxAware;
import de.upb.sede.param.IServiceParameterizationDesc;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceDesc.Builder.class)
public interface IServiceDesc extends IQualifiable, CommentAware, DynamicAuxAware {

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


