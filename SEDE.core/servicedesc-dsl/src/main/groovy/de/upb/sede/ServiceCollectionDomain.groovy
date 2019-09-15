package de.upb.sede

import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.types.MutableDataTypeDesc

class ServiceCollectionDomain extends DomainAware<MutableServiceCollectionDesc, Object> {

    /**
     * Redefines the service with the given qualifier by running the given describer against it.
     * A new service will be created if the qualifier is unused in this service collection.
     */
    def service(String qualifier, @DelegatesTo(ServiceDomain) Closure describer) {
        /*
         * Find or create service:
         */
        def service = model.services.find {it.qualifier == qualifier}

        if(service == null) {
            service = MutableServiceDesc.create().setQualifier(qualifier)
            model.services += service
        }

        /*
         * Redefine service:
         */
        delegateDown(new ServiceDomain(), service,  describer)

        return service
    }

    /**
     * Redefines the type with the given qualifier by running the given describer against it.
     * A new type will be created if the qualifier is unused in this service collection.
     */
    def type(String qualifier, @DelegatesTo(DataTypeDomain) Closure describer) {
        /*
         * Find or create datatype:
         */
        def dataType = model.dataTypes.find{ it.qualifier == qualifier }

        if(dataType == null) {
            dataType = MutableDataTypeDesc.create()
                .setQualifier(qualifier)
                .setSemanticType(qualifier)
            model.dataTypes += dataType
        }

        delegateDown(new DataTypeDomain(), dataType, describer)

        return dataType
    }

    def getService(String qualifier) {
        model.services.find {it.qualifier == qualifier}
    }

    
    def getType(String qualifier) {
        model.dataTypes.find {it.qualifier == qualifier}
    }

    @Override
    String getBindingName() {
        return "collection"
    }
}
