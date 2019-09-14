package de.upb.sede


import de.upb.sede.exec.MethodParameterDesc
import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableSignatureDesc
import groovy.transform.PackageScope

class MethodDomain extends DomainAware {

    ServiceDomain serviceDom

    @PackageScope
    MutableMethodDesc method() {
        model as MutableMethodDesc
    }

    MutableSignatureDesc signature(@DelegatesTo(MutableSignatureDesc) Closure signatureDescriber) {
        return addSignature(MutableSignatureDesc.create(), signatureDescriber)
    }

    MutableSignatureDesc signature(Map<String, List<String>> types,
                            @DelegatesTo(MutableSignatureDesc) Closure signatureDescriber) {
        def newSign = MutableSignatureDesc.create()
        if("inputs" in types || "ins" in types) {
            List<String> inputs = "inputs" in types? types["inputs"] : types["ins"]
            newSign.inputs += inputs.collect { MethodParameterDesc.builder().type(it).build() }
        }
        if("outputs" in types || "outs" in types) {
            List<String> outputs = "outputs" in types? types["outputs"] : types["outs"]
            newSign.outputs += outputs.collect { MethodParameterDesc.builder().type(it).build() }
        }
        return addSignature(newSign, signatureDescriber)
    }

    private MutableSignatureDesc addSignature(MutableSignatureDesc signatureDesc, @DelegatesTo(MutableSignatureDesc) Closure signatureDescriber) {
        def signatureDom = new MethodSignatureDomain( model: signatureDesc)
        delegateDown(signatureDom, signatureDescriber)

        // TODO check if the signature is already existing
        method().signatures += signatureDesc
        return signatureDesc
    }

    def signatures(@DelegatesTo(MethodSignatureDomain) Closure describer) {
        method().signatures.each {
            def signatureDom = new MethodSignatureDomain(model: it)
            delegateDown(signatureDom, describer)
        }
    }

    @Override
    def String getBindingName() {
        return "method"
    }
}
