package de.upb.sede

import de.upb.sede.exec.IMethodDesc
import de.upb.sede.exec.IServiceDesc
import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableMethodParameterDesc
import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.param.MutableServiceParameterizationDesc
import groovy.transform.PackageScope

class ServiceDomain
    extends DomainAware<MutableServiceDesc, ServiceCollectionDomain>
    implements
        Shared.CommentAware,
        Shared.AuxDomAware {

    def setStateful(Closure typeDescriber = Closure.IDENTITY) {
        setStateType(model.qualifier, typeDescriber)
    }

    @PackageScope
    def setStateType(String dataTypeQualifier, Closure typeDescriber = Closure.IDENTITY) {
        topDomain.type(dataTypeQualifier, typeDescriber)
        model.fieldTypes[IServiceDesc.STATE_FIELD] = dataTypeQualifier
    }


    def method(Map methodDef, @DelegatesTo(MethodDomain) Closure methodDescriber = defaults.method) {
        if(! "name" in methodDef) {
            throw new RuntimeException("Provided method declaration needs to define method name. Provided method definition: " + methodDef.toString())
        }

        String methodName = methodDef["name"] as String
        def signatureDef = new HashMap(methodDef)
        signatureDef.remove("name")

        def methodDesc = getOrDefineMethod (methodName, methodDef);

        def mDom = new MethodDomain()
        delegateDown(mDom, methodDesc, methodDescriber)
        return methodDesc
    }

    private MutableMethodDesc getOrDefineMethod(String methodQualifier, Map methodDefs) {
//                                                @DelegatesTo(MethodDomain) Closure describer = defaults.method) {
        def methodDesc = MethodDomain.createMethod(methodQualifier, methodDefs)
        def matchingMethod = model.methods.find { Shared.matchingMethods(it, methodDesc) }
        if(matchingMethod != null) {
            return matchingMethod
        } else {
            model.methods.add(methodDesc)
            return methodDesc;
        }
    }


    def constructor() {
        return constructor([:], defaults.constructor)
    }

    def constructor(Map signatureDef, @DelegatesTo(MethodDomain) Closure signatureDescriber = defaults.constructor) {
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

    @PackageScope
    @Override
    String getBindingName() {
        "service"
    }

}
