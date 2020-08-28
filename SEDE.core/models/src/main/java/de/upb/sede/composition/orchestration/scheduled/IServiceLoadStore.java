package de.upb.sede.composition.orchestration.scheduled;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IServiceInstanceStorageNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceLoadStore.Builder.class)
public interface IServiceLoadStore extends ScheduledOperation {

    IServiceInstanceStorageNode getServiceInstanceStorageNode();

}

