package de.upb.sede.composition.graphs.nodes;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.WithField;
import org.immutables.value.Value;

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

    String getServiceClasspath();

    boolean isLoadInstruction();

    @Override
    default String getText() {
        return String.format("%s service %s", isLoadInstruction()?"load":"store", getServiceInstanceFieldName());
    }

}
