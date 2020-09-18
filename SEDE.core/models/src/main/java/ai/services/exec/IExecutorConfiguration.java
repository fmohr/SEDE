package ai.services.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;
import java.util.List;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorConfiguration.Builder.class)
public interface IExecutorConfiguration {

    @Nullable
    String getExecutorId();

    @Value.Default
    default String getServiceStoreLocation() {
        return "sede-service-instances";
    }

    List<String> getCapabilities();

    List<String> getServices();

    List<String> getGateways();

    @Value.Default
    default int getThreadNumber() {
        return 4;
    }

}
