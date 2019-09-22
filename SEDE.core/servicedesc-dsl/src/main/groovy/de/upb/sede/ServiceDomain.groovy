package de.upb.sede

import de.upb.sede.exec.IMethodDesc
import de.upb.sede.exec.IServiceDesc
import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.exec.aux.MutableJavaDispatchAux

class ServiceDomain
    extends DomainAware<MutableServiceDesc, ServiceCollectionDomain>
    implements Shared.JavaDispatchAware, Shared.CommentAware {

    def setStateful(Closure typeDescriber = Closure.IDENTITY) {
        setStateType(model.qualifier, typeDescriber)
    }

    def setStateType(String dataTypeQualifier, Closure typeDescriber = Closure.IDENTITY) {
        topDomain.type(dataTypeQualifier, typeDescriber)
        model.fieldTypes[IServiceDesc.STATE_FIELD] = dataTypeQualifier
    }

    def method(Map signatureDef,  String name, @DelegatesTo(MethodSignatureDomain) Closure signatureDescriber = defaults.method) {
        def methodDef = new HashMap(signatureDef)
        methodDef["name"] = name
        return method(methodDef, signatureDescriber)
    }


    def method(Map methodDef, @DelegatesTo(MethodSignatureDomain) Closure signatureDescriber = defaults.method) {
        if(! "name" in methodDef) {
            throw new RuntimeException("Provided method declaration needs to define method name. Provided method definition: " + methodDef.toString())
        }

        String methodName = methodDef["name"] as String
        def signatureDef = new HashMap(methodDef)
        signatureDef.remove("name")
        return overloadMethod (methodName) { signature(signatureDef, signatureDescriber) }
    }

    def overloadMethod(String qualifier, @DelegatesTo(MethodDomain) Closure describer = defaults.method) {
        def m = model.methods.find {it.qualifier == qualifier}

        if(m == null) {
            m = MutableMethodDesc
                .create()
                .setQualifier(qualifier)
            model.methods += m
        }

        def mDom = new MethodDomain()
        delegateDown(mDom, m, describer)

        return m
    }


    def constructor() {
        return constructor([:], defaults.constructor)
    }

    def constructor(@DelegatesTo(MethodDomain) Closure signatureDescriber) {
        def m = overloadMethod(IMethodDesc.CONSTRUCTOR_METHOD_NAME, signatureDescriber)
        return m
    }

    def constructor(Map signatureDef, @DelegatesTo(MethodSignatureDomain) Closure signatureDescriber = defaults.constructor) {
        def methodDef = new HashMap(signatureDef)
        methodDef["name"] = IMethodDesc.CONSTRUCTOR_METHOD_NAME
        def m = method(methodDef, signatureDescriber)
        return m
    }

    def eachMethod(@DelegatesTo(MethodSignatureDomain) Closure describer) {
        model.methods.each {
            def methodDom =  new MethodDomain()
            delegateDown(methodDom, it, describer)
        }
    }

    def implement(String... additionalInterfaces) {
        model.interfaces += additionalInterfaces
    }

    def implement(IServiceDesc... additionalInterfaces) {
        model.interfaces += additionalInterfaces.collect {it.qualifier}
    }

    @Override
    def String getBindingName() {
        "service"
    }
}
