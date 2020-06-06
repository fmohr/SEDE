package de.upb.sede.composition.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.upb.sede.util.TypeDeserializationDelegate;
import org.immutables.value.Value;

@JsonDeserialize(using = TypeClass.DeserializeDelegate.class)
public interface TypeClass {

    @Value.Lazy
    default String getTypeClass() {
        return TypeDeserializationDelegate.stripPrefix(this.getClass().getSimpleName());
    }

    @Value.Lazy
    String getTypeQualifier();

    class DeserializeDelegate extends TypeDeserializationDelegate<TypeClass> {

        protected DeserializeDelegate() {
            super(TypeClass.class);
        }

        protected DeserializeDelegate(Class<?> vc) {
            super(vc);
        }

        @Override
        protected String getTypeField() {
            return "typeClass";
        }
    }

}
