package de.upb.sede.composition.orchestration.emulated;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.graphs.nodes.IServiceInstanceStorageNode;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceLoadStoreOp.Builder.class)
public interface IServiceLoadStoreOp extends EmulatedOp {

    IServiceInstanceStorageNode getServiceInstanceStorageNode();

}

