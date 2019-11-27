package de.upb.sede.composition.graphs.typing;

import de.upb.sede.ISDLLookupService;
import de.upb.sede.exec.IMethodDesc;
import de.upb.sede.exec.IMethodRef;
import de.upb.sede.exec.IServiceDesc;
import de.upb.sede.exec.ISignatureDesc;

import java.util.ArrayList;
import java.util.List;

class MethodCognition {

    private final IServiceDesc serviceDesc;

    private final IMethodDesc methodDesc;

    private final ISignatureDesc signatureDesc;

    private final IMethodRef methodRef;

    private List<TypeCoercion> inputParamTypeCoercions = new ArrayList<>();

    public MethodCognition(IServiceDesc service, IMethodDesc method, ISignatureDesc signature, IMethodRef methodRef) {
        this.serviceDesc = service;
        this.methodDesc = method;
        this.signatureDesc = signature;
        this.methodRef = methodRef;
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

}
