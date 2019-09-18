package de.upb.sede


import de.upb.sede.exec.MethodParameterDesc
import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableSignatureDesc
import groovy.transform.PackageScope

class MethodDomain extends DomainAware<MutableMethodDesc, ServiceDomain> {

    MutableSignatureDesc signature(@DelegatesTo(MutableSignatureDesc) Closure signatureDescriber) {
        return addSignature(MutableSignatureDesc.create(), signatureDescriber)
    }

    MutableSignatureDesc signature(Map<String, List<String>> types,
                            @DelegatesTo(MutableSignatureDesc) Closure signatureDescriber) {
        def newSign = MutableSignatureDesc.create()
        if("inputs" in types || "ins" in types) {
            List<String> inputs = "inputs" in types? types["inputs"] : types["ins"]
            newSign.inputs += inputs.collect { MethodParameterDesc.builder().type(it).build() }
        } else if("input" in types) {
            def inputType = MethodParameterDesc.builder()
                .type(types["input"] as String)
                .build()
            newSign.inputs += inputType
        }
        if("outputs" in types || "outs" in types) {
            List<String> outputs = "outputs" in types? types["outputs"] : types["outs"]
            newSign.outputs += outputs.collect { MethodParameterDesc.builder().type(it).build() }
        } else if("output" in types) {
            def outputType = MethodParameterDesc.builder()
                .type(types["output"] as String)
                .build()
            newSign.outputs += outputType
        }
        return addSignature(newSign, signatureDescriber)
    }

    private MutableSignatureDesc addSignature(MutableSignatureDesc signatureDesc, @DelegatesTo(MutableSignatureDesc) Closure signatureDescriber) {
        delegateDown(new MethodSignatureDomain(), signatureDesc, signatureDescriber)

        // TODO check if the signature already exists
        model.signatures += signatureDesc
        return signatureDesc
    }

    def eachSignature(@DelegatesTo(MethodSignatureDomain) Closure describer) {
        model.signatures.each {
            def signatureDom = new MethodSignatureDomain()
            delegateDown(signatureDom, it, describer)
        }
    }

    @Override
    def String getBindingName() {
        return "method"
    }
}
