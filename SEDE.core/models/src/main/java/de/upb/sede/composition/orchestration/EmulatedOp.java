package de.upb.sede.composition.orchestration;

import de.upb.sede.composition.types.TypeClass;
import de.upb.sede.util.TypeDeserializationDelegate;
import org.immutables.value.Value;

import java.util.List;

public interface EmulatedOp {

    @Value.Default
    default boolean wasHandled() {
        return false;
    }

    List<String> getDFields();

    @Value.Lazy
    default String getEmOpType() {
        return TypeDeserializationDelegate.stripPrefix(this.getClass().getSimpleName());
    }

    class DeserializeDelegate extends TypeDeserializationDelegate<TypeClass> {

        protected DeserializeDelegate() {
            super(TypeClass.class);
        }

        protected DeserializeDelegate(Class<?> vc) {
            super(vc);
        }

        @Override
        protected String getTypeField() {
            return "emulatedOperationType";
        }
    }

}
