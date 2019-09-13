package de.upb.sede

import de.upb.sede.exec.MutableServiceDesc
import de.upb.sede.types.MutableDataTypeDesc
import groovy.transform.PackageScope

class DataTypeDomain implements ModelAware {

    ServiceCollectionDomain collectionDom

    @PackageScope
    MutableDataTypeDesc type() {
        model as MutableDataTypeDesc
    }


}
