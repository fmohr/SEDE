package ai.services.exec.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = JavaParameterizationAux.Builder.class)
public interface IJavaParameterizationAux {

    @Nullable
    IJavaDispatchAux getParameterHandler();

    @Value.Default
    default boolean  getAutoScanEachParam() {
        return false;
    }

    @Value.Default
    default boolean  getBundleInMap() {
        return false;
    }

    @Value.Default
    default boolean  getBundleInArray() {
        return false;
    }
    @Value.Default
    default boolean  getBundleInList() {
        return false;
    }

    @Value.Default
    default boolean getPrecedeParamsWithNames() {
        return false;
    }

    @Nullable
    List<String> getParamOrder();

}
