package de.upb.sede

import de.upb.sede.exec.IServiceDesc
import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.types.MutableDataTypeDesc
import groovy.transform.PackageScope

class ServiceDomain implements ModelAware {

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

    def method(String qualifier, Closure describer) {
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

        def methodDom = new MethodDomain(serviceDom: this, model: method)
        methodDom.run(describer)

        def newMethod = method.toImmutable()
        service().methods.removeIf {it.qualifier == qualifier}
        service().methods += newMethod
        return newMethod
    }

}
