package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.WithField;
import org.immutables.value.Value;

import javax.annotation.Nullable;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = ServiceInstanceStorageNode.Builder.class)
public interface IServiceInstanceStorageNode extends BaseNode,
    WithField{

    @Value.Lazy
    default String getServiceInstanceFieldName() {
        return getFieldName();
    }

    @Nullable
    String getInstanceIdentifier();

    String getServiceClasspath();

    boolean isLoadInstruction();

    @Override
    default String getText() {
        return String.format("%s service %s", isLoadInstruction()?"load":"store", getServiceInstanceFieldName());
    }

}
