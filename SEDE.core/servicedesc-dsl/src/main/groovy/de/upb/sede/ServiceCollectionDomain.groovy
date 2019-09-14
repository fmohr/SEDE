package de.upb.sede

import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.types.MutableDataTypeDesc
import groovy.transform.PackageScope

class ServiceCollectionDomain extends DomainAware {

    def service(String qualifier, @DelegatesTo(ServiceDomain) Closure describer) {
        def service = MutableServiceDesc.create()
            .setQualifier(qualifier)

        /*
         * Overwrite old service description
         */
        collection().services.stream()
            .filter { it.qualifier == qualifier }
            .findAny()
            .ifPresent { service.from(it) }

        def serviceDom = new ServiceDomain(collectionDom: this, model: service)
        delegateDown(serviceDom, describer)

        def serviceDesc = serviceDom.service ()
        collection().services.removeIf {it.qualifier == qualifier}
        collection().services += serviceDesc
        return serviceDesc
    }

    def getService(String qualifier) {
        collection().services.find {it.qualifier == qualifier}
    }

    @PackageScope
    MutableServiceCollectionDesc collection() {
        this.model as MutableServiceCollectionDesc
    }


    def type(String qualifier, @DelegatesTo(DataTypeDomain) Closure describer) {
        def dataType = MutableDataTypeDesc.create()
            .setQualifier(qualifier)
            .setSemanticType(qualifier)


        /*
         * Overwrite old type description
         */
        collection().dataTypes.stream()
            .filter { it.qualifier == qualifier }
            .findAny()
            .ifPresent { dataType.from(it) }

        def typeDom = new DataTypeDomain(collectionDom: this,
            model: dataType)
        delegateDown(typeDom, describer)

        def dataTypeDesc = typeDom.type()
        collection().dataTypes.removeIf {it.qualifier == qualifier}
        collection().dataTypes += dataTypeDesc
        return dataType
    }

    def getType(String qualifier) {
        collection().dataTypes.find {it.qualifier == qualifier}
    }

    @Override
    String getBindingName() {
        return "collection"
    }
}
