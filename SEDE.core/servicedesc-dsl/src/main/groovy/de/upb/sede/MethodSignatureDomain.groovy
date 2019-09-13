package de.upb.sede

import de.upb.sede.exec.MethodParameterDesc
import de.upb.sede.exec.MutableMethodParameterDesc
import de.upb.sede.exec.MutableSignatureDesc
import groovy.transform.NamedVariant
import groovy.transform.PackageScope

class MethodSignatureDomain implements ModelAware{

    @PackageScope
    MutableSignatureDesc signature() {
        return model
    }

    void setInputTypes(String... inputTypes) {
        signature().inputs.clear()
        addInputTypes(inputTypes)
    }

    void addInputTypes(String... inputTypes) {
        for(String inputType: inputTypes) {
            signature().inputs += param {
                type = inputType
            }
        }
    }

    void setOutputTypes(String... outputTypes) {
        signature.outputs.clear()
        addOutputTypes(outputTypes)
    }

    void addOutputTypes(String... outputTypes) {
        for(String outputType: outputTypes) {
            signature().outputs += param {
                type = outputType
            }
        }
    }

    @NamedVariant
    void addInput(String type, String name) {
        String t = type
        String n = name
        signature().inputs += param type: type, name: name
    }

    @NamedVariant
    void addOutput(String type, String name) {
        signature().outputs += param type: type, name: name
    }

    static MethodParameterDesc param(String t, String n) {
        return param {
            type = t
            name = n
        }
    }

    static MethodParameterDesc param(Map<String, String> paramDesc) {
        return param(paramDesc["type"], paramDesc["name"])
    }

    static MethodParameterDesc param(@DelegatesTo(MutableMethodParameterDesc) Closure paramDescriber) {
        def parameter = MutableMethodParameterDesc.create()
        paramDescriber.delegate = parameter
        paramDescriber.resolveStrategy = Closure.DELEGATE_ONLY
        paramDescriber.run()
        return parameter.toImmutable()
    }
}
