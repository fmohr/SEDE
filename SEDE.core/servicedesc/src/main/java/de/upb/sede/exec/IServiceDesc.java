package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.ICommented;
import de.upb.sede.IQualifiable;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.aux.IJavaDispatchAux;
import de.upb.sede.exec.aux.IPythonClassAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceDesc.Builder.class)
public interface IServiceDesc extends IQualifiable, ICommented {

    public static final String STATE_FIELD = "state";

    List<IMethodDesc> getMethods();

    List<String> getInterfaces();

    @Value.Default
    default boolean isAbstract() {
        return false;
    }

    Map<String, String> getFieldTypes();

    @Nullable
    IJavaDispatchAux getJavaAux();

    @Nullable
    IPythonClassAux getPythonClassAuxiliaries();

    @Nullable
    @Value.Auxiliary
    default String getStateType() {
        return getFieldTypes().getOrDefault(STATE_FIELD, null);
    }

}


