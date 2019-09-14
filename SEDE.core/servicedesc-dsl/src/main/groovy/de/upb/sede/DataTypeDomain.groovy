package de.upb.sede


import de.upb.sede.types.MutableDataTypeDesc
import groovy.transform.PackageScope

class DataTypeDomain extends DomainAware {

    ServiceCollectionDomain collectionDom

    @PackageScope
    MutableDataTypeDesc type() {
        model as MutableDataTypeDesc
    }

    @Override
    def String getBindingName() {
        "type"
    }
}
