package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.ICommented;
import de.upb.sede.IQualifiable;
import de.upb.sede.SModelStyle;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
import de.upb.sede.exec.auxiliary.IPythonClassAux;
import de.upb.sede.param.IServiceParameterizationDesc;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@SModelStyle
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
    IJavaDispatchAux getJavaDispatchAux();

    @Nullable
    IPythonClassAux getPythonClassAuxiliaries();

    @Nullable
    IServiceParameterizationDesc getServiceParameters();

    @Nullable
    @Value.Auxiliary
    default String getStateType() {
        return getFieldTypes().getOrDefault(STATE_FIELD, null);
    }

}


