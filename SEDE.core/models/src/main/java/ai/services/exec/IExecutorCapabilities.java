package ai.services.exec;

import java.util.List;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ExecutorCapabilities.Builder.class)
public interface IExecutorCapabilities {

    List<String> getFeatures();

    List<String> getServices();

}
