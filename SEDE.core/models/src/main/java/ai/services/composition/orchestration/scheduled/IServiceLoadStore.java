package ai.services.composition.orchestration.scheduled;

import ai.services.SEDEModelStyle;
import ai.services.composition.graphs.nodes.IServiceInstanceStorageNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceLoadStore.Builder.class)
public interface IServiceLoadStore extends ScheduledOperation {

    IServiceInstanceStorageNode getServiceInstanceStorageNode();

}

