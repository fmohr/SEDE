package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.ICommented;
import de.upb.sede.IQualifiable;
import de.upb.sede.SModelStyle;
import de.upb.sede.exec.aux.IJavaReflectionAux;
import de.upb.sede.exec.aux.IPythonClassAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    IJavaReflectionAux getJavaAux();

    @Nullable
    IPythonClassAux getPythonClassAuxiliaries();

    default Optional<String> getStateType() {
        return Optional.ofNullable(getFieldTypes().getOrDefault(STATE_FIELD, null));
    }

}


