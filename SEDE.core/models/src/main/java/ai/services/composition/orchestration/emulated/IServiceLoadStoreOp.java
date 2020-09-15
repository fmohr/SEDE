package ai.services.composition.orchestration.emulated;

import ai.services.SEDEModelStyle;
import ai.services.composition.graphs.nodes.IServiceInstanceStorageNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceLoadStoreOp.Builder.class)
public interface IServiceLoadStoreOp extends EmulatedOp {

    IServiceInstanceStorageNode getServiceInstanceStorageNode();

}

