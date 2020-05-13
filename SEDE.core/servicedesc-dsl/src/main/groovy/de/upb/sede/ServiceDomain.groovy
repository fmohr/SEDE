package de.upb.sede

import de.upb.sede.exec.IMethodDesc
import de.upb.sede.exec.IServiceDesc
import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.exec.auxiliary.MutableJavaDispatchAux
import de.upb.sede.param.MutableServiceParameterizationDesc
import de.upb.sede.util.DynTypeField

class ServiceDomain
    extends DomainAware<MutableServiceDesc, ServiceCollectionDomain>
    implements
//        Shared.AuxAware<MutableJavaDispatchAux>,
        Shared.CommentAware,
        Shared.AuxDomAware {

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

    def constructor(Map signatureDef, @DelegatesTo(MethodSignatureDomain) Closure signatureDescriber = defaults.constructor) {
        def methodDef = new HashMap(signatureDef)
        methodDef["name"] = IMethodDesc.CONSTRUCTOR_METHOD_NAME
        methodDef["output"] = model.qualifier
        methodDef["static"] = true
        def m = method(methodDef, signatureDescriber)
        return m
    }

    def params(@DelegatesTo(ParameterDomain) Closure describer) {
        Objects.requireNonNull(describer)

        if(model.serviceParameters == null) {
            model.serviceParameters = MutableServiceParameterizationDesc.create()
        }

        def paramDom = new ParameterDomain()
        delegateDown(paramDom, model.serviceParameters, describer)

        return model.serviceParameters
    }

    def eachMethod(@DelegatesTo(MethodDomain) Closure describer) {
        model.methods.each {
            def methodDom =  new MethodDomain()
            delegateDown(methodDom, it, describer)
        }
    }

    def implOf(String... additionalInterfaces) {
        model.interfaces += additionalInterfaces.collect()
    }

    def implOf(IServiceDesc... additionalInterfaces) {
        model.interfaces += additionalInterfaces.collect {it.qualifier}
    }

    @Override
    def String getBindingName() {
        "service"
    }

//    @Override
//    MutableJavaDispatchAux setJavaAux(MutableJavaDispatchAux javaAux) {
//        if(model.dynAux == null) {
//            model.dispatchAux = new DynTypeField()
//        }
//        model.dynAux.set(MutableJavaDispatchAux, javaAux)
//        return javaAux
//    }
//
//
//    @Override
//    MutableJavaDispatchAux getJavaAux() {
//        if(model.dynAux == null) {
//            model.dispatchAux = new DynTypeField()
//        }
//        return model.dynAux.cast(MutableJavaDispatchAux)
//    }
}
