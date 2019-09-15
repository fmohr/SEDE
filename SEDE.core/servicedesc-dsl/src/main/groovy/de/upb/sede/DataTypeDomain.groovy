package de.upb.sede


import de.upb.sede.types.MutableDataTypeDesc
import groovy.transform.PackageScope

class DataTypeDomain extends DomainAware<MutableDataTypeDesc, ServiceCollectionDomain> {

    @Override
    def String getBindingName() {
        "type"
    }
}
