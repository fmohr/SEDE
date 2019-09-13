package de.upb.sede

import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableSignatureDesc
import de.upb.sede.exec.SignatureDesc
import groovy.transform.PackageScope

class MethodDomain implements ModelAware {

    ServiceDomain serviceDom

    @PackageScope
    MutableMethodDesc method() {
        model as MutableMethodDesc
    }

    SignatureDesc signature(@DelegatesTo(MutableSignatureDesc) Closure signatureDescriber) {
        def signature = MutableSignatureDesc.create()
        def signatureDom = new MethodSignatureDomain(model: signature)
        signatureDom.run(signatureDescriber)

        def newSignature = signature.toImmutable()
        // TODO check if the signature is already existing
        method().signatures += newSignature

        return newSignature
    }

}
