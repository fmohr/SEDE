package de.upb.sede.composition.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import org.immutables.value.Value;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = RefType.Builder.class)
public interface IRefType extends TypeClass {

    String SERVICE_INSTANCE_HANDLE_TYPE = "ServiceInstanceHandle";

    String SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE = "ServiceInstanceHandle.json";

    ValueTypeClass getTypeOfRef();

    @Override
    @Value.Lazy
    default String getTypeQualifier() {
        return getTypeOfRef().getTypeQualifier();
    }


}
