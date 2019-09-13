package de.upb.sede

import de.upb.sede.exec.MutableMethodDesc
import groovy.transform.PackageScope

class MethodDomain implements ModelAware {

    ServiceDomain serviceDom

    @PackageScope
    MutableMethodDesc method() {
        model as MutableMethodDesc
    }

    


}
