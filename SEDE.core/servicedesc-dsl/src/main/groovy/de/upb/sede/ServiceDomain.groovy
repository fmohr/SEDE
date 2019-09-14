package de.upb.sede

import de.upb.sede.exec.IMethodDesc
import de.upb.sede.exec.IServiceDesc
import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableServiceDesc
import groovy.transform.PackageScope

class ServiceDomain extends DomainAware{

    ServiceCollectionDomain collectionDom

    @PackageScope
    MutableServiceDesc service() {
        model as MutableServiceDesc
    }

    def setStateful(Closure typeDescriber = Closure.IDENTITY) {
        setStateType(service().qualifier, typeDescriber)
    }

    def setStateType(String dataTypeQualifier, Closure typeDescriber = Closure.IDENTITY) {
        collectionDom.type(dataTypeQualifier, typeDescriber)
        service().fieldTypes[IServiceDesc.STATE_FIELD] = dataTypeQualifier
    }

    def method(String qualifier, @DelegatesTo(MethodDomain) Closure describer) {
        def method = MutableMethodDesc
            .create()
            .setQualifier(qualifier)

        /*
         * Overwrite the old method description
         */
        service().methods.stream()
            .filter {it.qualifier == qualifier}
            .findAny()
            .ifPresent {method.from(it)}

        def methodDom = new MethodDomain( serviceDom: this, model: method)
        delegateDown(methodDom, describer)

        def newMethod = method
        service().methods.removeIf {it.qualifier == qualifier}
        service().methods += newMethod
        return newMethod
    }
    def method(Map methodDef, @DelegatesTo(MethodSignatureDomain) Closure signatureDescriber = Closure.IDENTITY) {
        if(! "name" in methodDef) {
            throw new RuntimeException("Provided method declaration needs to define method name. Provided method definition: " + methodDef.toString())
        }

        String methodName = methodDef["name"] as String
        def signatureDef = new HashMap(methodDef)
        signatureDef.remove("name")
        return method (methodName) { signature(signatureDef, signatureDescriber) }
    }

    def constructor(@DelegatesTo(MethodDomain) Closure signatureDescriber) {
        return method(IMethodDesc.CONSTRUCTOR_METHOD_NAME, signatureDescriber)
    }

    def constructor(Map methodDef, @DelegatesTo(MethodSignatureDomain) Closure signatureDescriber = Closure.IDENTITY) {
        def signatureDef = new HashMap(methodDef)
        signatureDef["name"] = IMethodDesc.CONSTRUCTOR_METHOD_NAME
        return method(signatureDef, signatureDescriber)
    }

    def methods(@DelegatesTo(MethodSignatureDomain) Closure describer) {
        service().methods.each {
            def methodDom =  new MethodDomain( serviceDom: this, model: it)
            delegateDown(methodDom, describer)
        }
    }

    @Override
    def String getBindingName() {
        "service"
    }
}
