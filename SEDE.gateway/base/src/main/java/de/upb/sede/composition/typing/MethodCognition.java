package de.upb.sede.composition.typing;

import de.upb.sede.composition.ITypeCoercion;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodRef;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.ISignatureDesc;

import java.util.List;

public class MethodCognition {

    private IMethodRef methodRef;

    private ISignatureDesc signatureDesc;

    private IMethodDesc methodDesc;

    private IServiceDesc serviceDesc;

    private List<ITypeCoercion> typeCoercionList;


    MethodCognition(IMethodRef methodRef, ISignatureDesc signatureDesc, IMethodDesc methodDesc, IServiceDesc serviceDesc, List<ITypeCoercion> typeCoercionList) {
        this.methodRef = methodRef;
        this.signatureDesc = signatureDesc;
        this.methodDesc = methodDesc;
        this.serviceDesc = serviceDesc;
        this.typeCoercionList = typeCoercionList;
    }

    public IMethodRef getMethodRef() {
        return methodRef;
    }

    public ISignatureDesc getSignatureDesc() {
        return signatureDesc;
    }

    public IMethodDesc getMethodDesc() {
        return methodDesc;
    }

    public IServiceDesc getServiceDesc() {
        return serviceDesc;
    }

    public List<ITypeCoercion> getTypeCoercionList() {
        return typeCoercionList;
    }
}
