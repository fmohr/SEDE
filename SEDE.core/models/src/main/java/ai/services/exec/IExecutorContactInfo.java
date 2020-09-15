package ai.services.exec;

import ai.services.IQualifiable;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorContactInfo.Builder.class)
public interface IExecutorContactInfo extends IQualifiable {

    @Nullable
    String getURL();

    @Value.Default
    default boolean isReachable() {
        return true;
    }
}
