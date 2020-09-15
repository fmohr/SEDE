package ai.services.composition.orchestration.emulated;

import ai.services.composition.types.TypeClass;
import ai.services.util.TypeDeserializationDelegate;
import org.immutables.value.Value;

import java.util.List;

public interface EmulatedOp {

    @Value.Default
    default boolean wasHandled() {
        return false;
    }

    List<String> getDFields();

    @Value.Lazy
    default String getEmOpKind() {
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
            return "emOpKind";
        }
    }

}
