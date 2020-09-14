package de.upb.sede.exec;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
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

    String getServiceStoreLocation();

    List<String> getCapabilities();

    List<String> getServices();

    List<String> getGateways();

    @Value.Default
    default int getThreadNumber() {
        return 4;
    }

}

