package ai.services.composition.typing;

import ai.services.composition.ITypeCoercion;
import ai.services.exec.IMethodDesc;
import ai.services.exec.IMethodRef;
import ai.services.exec.IServiceDesc;

import java.util.List;

public class MethodCognition {

    private IMethodRef methodRef;

    private IMethodDesc methodDesc;

    private IServiceDesc serviceDesc;

    private List<ITypeCoercion> typeCoercionList;

    MethodCognition(IMethodRef methodRef, IMethodDesc methodDesc, IServiceDesc serviceDesc, List<ITypeCoercion> typeCoercionList) {
        this.methodRef = methodRef;
        this.methodDesc = methodDesc;
        this.serviceDesc = serviceDesc;
        this.typeCoercionList = typeCoercionList;
    }

    public IMethodRef getMethodRef() {
        return methodRef;
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
