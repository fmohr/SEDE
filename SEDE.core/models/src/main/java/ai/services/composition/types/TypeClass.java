package ai.services.composition.types;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ai.services.util.TypeDeserializationDelegate;
import org.immutables.value.Value;

@JsonDeserialize(using = TypeClass.DeserializeDelegate.class)
public interface TypeClass {

    @Value.Lazy
    default String getTypeKind() {
        return TypeDeserializationDelegate.stripPrefix(this.getClass().getSimpleName());
    }

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
            return "typeKind";
        }
    }


    static ValueTypeClass tryDeref(TypeClass tc) {
        if(isRefType(tc)) {
            IRefType refType = (IRefType) tc;
            return refType.getTypeOfRef();
        } else if(tc instanceof ValueTypeClass) {
            return (ValueTypeClass) tc;
        } else {
            throw new IllegalArgumentException("Cannot dereference type " + tc);
        }
    }

    static boolean isRefType(TypeClass tc) {
        return tc instanceof IRefType;
    }

    static boolean isServiceHandle(TypeClass tc) {
        return tryDeref(tc) instanceof IServiceInstanceType;
    }

    static boolean isPrimitive(TypeClass tc) {
        return tc instanceof IPrimitiveValueType;
    }

}
