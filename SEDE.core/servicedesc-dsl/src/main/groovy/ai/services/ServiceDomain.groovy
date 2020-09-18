package ai.services

import ai.services.exec.IMethodDesc
import ai.services.exec.IServiceDesc
import ai.services.exec.MutableMethodDesc
import ai.services.exec.MutableServiceDesc
import ai.services.param.MutableServiceParameterizationDesc
import ai.services.util.DynRecord
import groovy.transform.PackageScope

class ServiceDomain
    extends DomainAware<MutableServiceDesc, ServiceCollectionDomain>
{

    // model MutableServiceDesc

    // topDom ServiceCollectionDomain

    static MutableServiceDesc aux(MutableServiceDesc model, @DelegatesTo(DynRecord) Closure desc) {
        Shared.aux(model, desc)
        return model
    }

    static MutableServiceDesc comment(MutableServiceDesc model, String ... comments) {
        Shared.comment(model, comments)
        return model
    }

    static MutableServiceDesc setInfo(MutableServiceDesc model, String commentBlock) {
        Shared.setInfo(model, commentBlock)
        return model
    }

    static def setStateful(MutableServiceDesc model) {
        setStateType(model, model.qualifier)
    }

    @PackageScope
    static def setStateType(MutableServiceDesc model, String dataTypeQualifier) {
//        topDomain.type(dataTypeQualifier, typeDescriber)
        model.fieldTypes[IServiceDesc.STATE_FIELD] = dataTypeQualifier
    }

    static def method(MutableServiceDesc model, Map methodDef) {
        return method(model, methodDef, Defaults.defaults.method)
    }
    static def method(MutableServiceDesc model, Map userMethodDef, @DelegatesTo(MutableMethodDesc) Closure methodDescriber) {
        def methodDef = Defaults.defaults.methodSignature + userMethodDef
        if(! "name" in methodDef) {
            throw new RuntimeException("Provided method declaration needs to define method name. Provided method definition: " + methodDef.toString())
        }

        String methodName = methodDef["name"] as String
        def signatureDef = new HashMap(methodDef)
        signatureDef.remove("name")

        def methodDesc = getOrDefineMethod(model, methodName, methodDef);

        def mDom = new MethodDomain()
        delegateDown(mDom, methodDesc, methodDescriber)
        return methodDesc
    }

    private static MutableMethodDesc getOrDefineMethod(MutableServiceDesc model, String methodQualifier, Map methodDefs) {
//                                                @DelegatesTo(MethodDomain) Closure describer = defaults.method) {
        def methodDesc = MethodDomain.createMethod(methodQualifier, methodDefs)
        def matchingMethod = model.methods.find { Shared.matchingMethods(it, methodDesc) }
        if(matchingMethod != null) {
            boolean mismatchDetected = false
            for (int i = 0; i < matchingMethod.inputs.size(); i++) {
                def existingInput =  matchingMethod.inputs.get(i)
                def definedInput = methodDesc.inputs.get(i)
                if(existingInput != definedInput) {
                    mismatchDetected = true
                }
            }
            for (int i = 0; i < matchingMethod.outputs.size(); i++) {
                def existingOutput =  matchingMethod.outputs.get(i)
                def definedOutput = methodDesc.outputs.get(i)
                if(existingOutput != definedOutput) {
                    mismatchDetected = true
                }
            }
            if(mismatchDetected) {
                throw new IllegalArgumentException("The defined method ${methodQualifier} already exists with different signature:" +
                    "\nDefined method:${methodDesc}" +
                    "\nExisting method:${matchingMethod}")
            } else {
                return matchingMethod
            }
        } else {
            model.methods.add(methodDesc)
            return methodDesc;
        }
    }


    static def constructor(MutableServiceDesc model) {
        return constructor(model, [:], Defaults.defaults.constructor)
    }

    static def constructor(MutableServiceDesc model, Map methodDef) {
        return constructor(model, methodDef, Defaults.defaults.constructor)
    }

    static def constructor(MutableServiceDesc model, @DelegatesTo(MutableMethodDesc) Closure signatureDescriber) {
        return constructor(model, Collections.emptyMap(), signatureDescriber)
    }

    static def constructor(MutableServiceDesc model, Map signatureDef, @DelegatesTo(MutableMethodDesc) Closure signatureDescriber) {
        def methodDef = new HashMap(signatureDef)
        if(!(methodDef.containsKey("name"))) {
            methodDef["name"] = IMethodDesc.CONSTRUCTOR_METHOD_NAME
        }
        methodDef["output"] = model.qualifier
        methodDef["static"] = true
        def m = method(model, methodDef, signatureDescriber)
        return m
    }

    static def params(MutableServiceDesc model, @DelegatesTo(MutableServiceParameterizationDesc) Closure describer) {
        Objects.requireNonNull(describer)

        if(model.serviceParameters == null) {
            model.serviceParameters = MutableServiceParameterizationDesc.create()
        }

        def paramDom = new ParameterDomain()
        delegateDown(paramDom, model.serviceParameters, describer)

        return model.serviceParameters
    }

    static def eachMethod(MutableServiceDesc model, @DelegatesTo(MutableMethodDesc) Closure describer) {
        model.methods.each {
            def methodDom =  new MethodDomain()
            delegateDown(methodDom, it, describer)
        }
    }

    static def implOf(MutableServiceDesc model, String... additionalInterfaces) {
        model.interfaces += additionalInterfaces.collect()
    }

    static def implOf(MutableServiceDesc model, IServiceDesc... additionalInterfaces) {
        model.interfaces += additionalInterfaces.collect {it.qualifier}
    }

}