package de.upb.sede.composition.graphs.typing;

import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodRef;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.ISignatureDesc;

import java.util.List;

class MethodCognition {

    private final IServiceDesc serviceDesc;

    private final IMethodDesc methodDesc;

    private final ISignatureDesc signatureDesc;

    private final IMethodRef methodRef;

    private final List<TypeCoercion> parameterTypeCoersion;

    public MethodCognition(IServiceDesc service, IMethodDesc method, ISignatureDesc signature, IMethodRef methodRef, List<TypeCoercion> parameterTypeCoersion) {
        this.serviceDesc = service;
        this.methodDesc = method;
        this.signatureDesc = signature;
        this.methodRef = methodRef;
        this.parameterTypeCoersion = parameterTypeCoersion;
    }

    public IMethodDesc getMethodDesc() {
        return methodDesc;
    }

    public IMethodRef getMethodRef() {
        return methodRef;
    }

    public ISignatureDesc getSignatureDesc() {
        return signatureDesc;
    }

    public IServiceDesc getServiceDesc() {
        return serviceDesc;
    }

    public List<TypeCoercion> getParameterTypeCoersion() {
        return parameterTypeCoersion;
    }
}
