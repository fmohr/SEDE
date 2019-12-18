package de.upb.sede


import de.upb.sede.exec.MethodParameterDesc
import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableMethodParameterDesc
import de.upb.sede.exec.MutableSignatureDesc
import groovy.transform.PackageScope

class MethodDomain
    extends DomainAware<MutableMethodDesc, ServiceDomain>
    implements Shared.CommentAware {

    MutableSignatureDesc signature(@DelegatesTo(MutableSignatureDesc) Closure signatureDescriber) {
        return addSignature(MutableSignatureDesc.create(), signatureDescriber)
    }

    MutableSignatureDesc signature(Map types,
                            @DelegatesTo(MutableSignatureDesc) Closure signatureDescriber) {
        def newSign = MutableSignatureDesc.create()

        def inputParamReader = (this.&readSignatureParam).curry(newSign, types, true)
        if('inputs' in types)
            inputParamReader("inputs")
        else if('ins' in types)
            inputParamReader('ins')
        else if('input' in types)
            inputParamReader('input')

        def outputParamReader = (this.&readSignatureParam).curry(newSign, types, false)
        if('outputs' in types)
            outputParamReader("outputs")
        else if('outs' in types)
            outputParamReader('outs')
        else if('output' in types)
            outputParamReader('output')

        if('static' in types && types['static'] == true)
            newSign.setIsContextFree(true)

        return addSignature(newSign, signatureDescriber)
    }

    private void readSignatureParam(MutableSignatureDesc signature, Map types, boolean isInput, String key) {
        if(!(key in types) || (types[key] == null)) {
            return
        }
        def dictParams = new ArrayList()
        // append list or a single entry.
        // Shared::readQualifier will check if the elements are of the right type.
        dictParams += types[key]
        def newParams = dictParams.collect {
            def param = MutableMethodParameterDesc.create()
            def typeQualifier = Shared.readQualifier(it)
            param.type = typeQualifier
            return param
        }

        (isInput ? signature.inputs : signature.outputs).addAll(newParams)
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
