package de.upb.sede

import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.types.MutableDataTypeDesc
import de.upb.sede.util.DynRecord

class ServiceCollectionDomain
    extends DomainAware<MutableServiceCollectionDesc, MutableSDLAssembly> {

    // model MutableServiceCollectionDesc

    // topDom MutableSDLAssembly

    static MutableServiceCollectionDesc comment(MutableServiceCollectionDesc model, String ... comments) {
        Shared.comment(model, comments)
        return model
    }

    static MutableServiceCollectionDesc setInfo(MutableServiceCollectionDesc model, String commentBlock) {
        Shared.setInfo(model, commentBlock)
        return model
    }


    /**
     * Redefines the service with the given qualifier by running the given describer against it.
     * A new service will be created if the qualifier is unused in this service collection.
     */
    static def service(MutableServiceCollectionDesc model, String qualifier, @DelegatesTo(MutableServiceDesc) Closure describer) {
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
    static def type(MutableServiceCollectionDesc model, String qualifier, @DelegatesTo(MutableDataTypeDesc) Closure describer) {
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

    static def getService(MutableServiceCollectionDesc model, String qualifier) {
        model.services.find {it.qualifier == qualifier}
    }


    static def getType(MutableServiceCollectionDesc model, String qualifier) {
        model.dataTypes.find {it.qualifier == qualifier}
    }

}
