package de.upb.sede.composition.graphs.typing;

import de.upb.sede.composition.graphs.types.*;

enum Locals {

    LOCALS;

    static boolean isRefType(TypeClass tc) {
        return tc instanceof IRefType;
    }


    static boolean isService(TypeClass tc) {
        if(isRefType(tc)) {
            IRefType refType = (IRefType) tc;
            return isService(refType.getTypeOfRef());
        }
        return tc instanceof IServiceInstanceType;
    }

    static IServiceInstanceType getServiceType(TypeClass tc) {
        assert isService(tc);
        if(isRefType(tc)) {
            return getServiceType(((IRefType) tc).getTypeOfRef());
        } else if(tc instanceof IServiceInstanceType) {
            return (IServiceInstanceType) tc;
        } else {
            throw new RuntimeException("No check before access bug. The type is not a service: " + typeToText(tc));
        }
    }

    static boolean isData(TypeClass tc) {
        if(isRefType(tc)) {
            IRefType refType = (IRefType) tc;
            return isService(refType.getTypeOfRef());
        }
        return (tc instanceof IDataValueType) || (tc instanceof IPrimitiveValueType);
    }

    static String typeToText(TypeClass tc) {
        if(isRefType(tc)) {
            return String.format("Ref<%s>", typeToText(((IRefType)tc).getTypeOfRef()));
        }
        return tc.getTypeClass();
    }
}
