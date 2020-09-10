package de.upb.sede.composition.types.serialization;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.SEDEModelStyle;
import de.upb.sede.composition.types.TypeClass;
import org.immutables.value.Value;

import javax.annotation.Nonnull;

import static de.upb.sede.composition.types.IRefType.SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE;

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
            .semanticName(SEMANTIC_SERVICE_INSTANCE_HANDLE_TYPE);
    }
}

