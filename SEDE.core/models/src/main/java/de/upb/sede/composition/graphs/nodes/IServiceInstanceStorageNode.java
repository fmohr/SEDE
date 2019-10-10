package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceInstanceStorageNode.Builder.class)
public interface IServiceInstanceStorageNode extends BaseNode {

    @Nullable
    String getInstanceId();

    String getServiceInstanceFieldName();

    String getServiceClasspath();

    @Value.Derived
    default boolean isLoadInstruction() {
        String instanceId = getInstanceId();
        return instanceId != null && !instanceId.isEmpty();
    }
}
