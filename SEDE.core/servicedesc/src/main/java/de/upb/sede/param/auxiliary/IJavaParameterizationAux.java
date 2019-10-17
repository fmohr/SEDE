package de.upb.sede.param.auxiliary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.exec.auxiliary.IJavaDispatchAux;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = JavaParameterizationAux.Builder.class)
public interface IJavaParameterizationAux {

    @Nullable
    IJavaDispatchAux getDispatchAux();

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
