package ai.services.composition.typing;

import ai.services.composition.graphs.nodes.MarshalNode;
import ai.services.composition.types.*;
import ai.services.composition.types.serialization.Marshalling;
import ai.services.IServiceRef;
import ai.services.SDLLookupService;
import ai.services.composition.graphs.nodes.IMarshalNode;
import ai.services.composition.types.serialization.IMarshalling;
import ai.services.core.PrimitiveType;
import ai.services.exec.IServiceDesc;
import ai.services.types.IDataTypeDesc;
import ai.services.types.IDataTypeRef;

import java.util.Optional;

public class TypeUtil {

    private static ValueTypeClass tryDeref(TypeClass tc) {
        if(isRefType(tc)) {
            IRefType refType = (IRefType) tc;
            return refType.getTypeOfRef();
        } else if(tc instanceof ValueTypeClass) {
            return (ValueTypeClass) tc;
        } else {
            throw new IllegalArgumentException("Cannot dereference type " + tc);
        }
    }

    public static boolean isRefType(TypeClass tc) {
        return tc instanceof IRefType;
    }

    public static boolean isService(TypeClass tc) {
        return tryDeref(tc) instanceof IServiceInstanceType;
    }

    public static IServiceInstanceType getServiceType(TypeClass tc) {
        assert isService(tc);
        ValueTypeClass serviceType = tryDeref(tc);
        if(serviceType instanceof IServiceInstanceType) {
            return (IServiceInstanceType) serviceType;
        } else {
            throw new RuntimeException("No check before access bug. The type is not a service: " + typeToText(tc));
        }
    }

    public static boolean isData(TypeClass tc) {
        ValueTypeClass vtc = tryDeref(tc);
        return (vtc instanceof IDataValueType) || (vtc instanceof IPrimitiveValueType);
    }

    public static boolean isDataValueType(TypeClass tc) {
        ValueTypeClass vtc = tryDeref(tc);
        return (vtc instanceof IDataValueType);
    }

    public static boolean isPrimitiveValueType(TypeClass tc) {
        ValueTypeClass vtc = tryDeref(tc);
        return (vtc instanceof IPrimitiveValueType);
    }


    public static String typeToText(TypeClass tc) {
        if(isRefType(tc)) {
            return String.format("Ref<%s>", typeToText(((IRefType)tc).getTypeOfRef()));
        }
        return tc.getTypeKind();
    }

    public static boolean isServiceHandleCastNode(IMarshalNode marshalNode) {
        return isService(marshalNode.getMarshalling().getValueType());
    }

    public static MarshalNode.Builder createCastToServiceHandleNode(IMarshalling.Direction direction,
                                                                    TypeClass serviceType) {
        if(!TypeUtil.isService(serviceType)) {
            throw new IllegalArgumentException("Type is not service: " + serviceType);
        }
        return MarshalNode.builder()
            .marshalling(Marshalling.builder()
                .direction(direction)
                .valueType(serviceType)
                .build());
    }

    public static TypeClass getTypeClassOf(SDLLookupService lookupService, String returnType) {
        /*
         * Assume type is a primitive qualifier, i.e. "Number", "String", "Bool"
         */
        Optional<PrimitiveType> primTypeOpt = PrimitiveType.insensitiveValueOf(returnType);
        if(primTypeOpt.isPresent()) {
            /*
             * Method cannot declare the NULL class as its return value.
             */
            if(primTypeOpt.get() == PrimitiveType.NULL) {
                throw TypeCheckException.methodReturnsNullClass();
            }
            IPrimitiveValueType primValType = PrimitiveValueType.builder()
                .primitiveType(primTypeOpt.get()).build();
            return primValType;
        }
        /*
         * Assume type is a service type qualifier.
         */
        IServiceRef serviceRef = IServiceRef.of(null, returnType);
        Optional<IServiceDesc> serviceDescOpt = lookupService.lookup(serviceRef);
        if(serviceDescOpt.isPresent()) {
            IServiceInstanceType serviceInstanceType = ServiceInstanceType.builder()
                .typeQualifier(returnType)
                .build();
            return serviceInstanceType;
        }
        /*
         * The last case remaining is a data value type.
         */
        IDataTypeRef dataTypeRef = IDataTypeRef.of(returnType);
        Optional<IDataTypeDesc> dataTypeOpt = lookupService.lookup(dataTypeRef);
        if(dataTypeOpt.isPresent()) {
            IDataValueType dataValueType = DataValueType.builder()
                .typeQualifier(returnType)
                .build();
            return dataValueType;
        }

        /*
         * The type qualifier was not recognized..
         */
        throw TypeCheckException.unknownType(returnType, "type");
    }
}
