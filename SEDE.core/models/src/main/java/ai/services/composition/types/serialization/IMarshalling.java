package ai.services.composition.types.serialization;

import ai.services.SEDEModelStyle;
import ai.services.composition.types.IRefType;
import ai.services.composition.types.TypeClass;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import javax.annotation.Nonnull;

@SEDEModelStyle
@Value.Immutable
@Value.Modifiable
@JsonDeserialize(builder = Marshalling.Builder.class)
public interface IMarshalling {

    enum Direction {
        MARSHAL, UNMARSHAL;

        public boolean isMarshal() {
            return this == MARSHAL;
        }

        public boolean isUnMarshal() {
            return this == UNMARSHAL;
        }
    }

    IMarshalling.Direction getDirection();

    @Nonnull
    TypeClass getValueType();

    String getSemanticName();

    static Marshalling.Builder buildServiceHandleMarshalling() {
        return Marshalling.builder()
            .semanticName(IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE);
    }
}

