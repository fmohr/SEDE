package de.upb.sede


import de.upb.sede.exec.IMethodParameterDesc

import de.upb.sede.exec.MutableMethodParameterDesc
import de.upb.sede.exec.MutableSignatureDesc
import de.upb.sede.exec.aux.MutableJavaReflectionAux
import groovy.transform.NamedVariant

class MethodSignatureDomain extends DomainAware<MutableSignatureDesc, MethodDomain> {

    void setInputTypes(String... inputTypes) {
        model.inputs.clear()
        addInputTypes(inputTypes)
    }

    void addInputTypes(String... inputTypes) {
        for(String inputType: inputTypes) {
            model.inputs += param {
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
            model.outputs += param {
                type = outputType
            }
        }
    }

    @NamedVariant
    void addInput(String type, String name) {
        String t = type
        String n = name
        model.inputs += param type: type, name: name
    }

    @NamedVariant
    void addOutput(String type, String name) {
        model.outputs += param type: type, name: name
    }

    static MutableMethodParameterDesc param(String t, String n) {
        return param {
            type = t
            name = n
        }
    }

    static MutableMethodParameterDesc param(Map<String, String> paramDesc) {
        return param(paramDesc["type"], paramDesc["name"])
    }

    static MutableMethodParameterDesc param(@DelegatesTo(MutableMethodParameterDesc) Closure paramDescriber) {
        def parameter = MutableMethodParameterDesc.create()
        paramDescriber.delegate = parameter
        paramDescriber.resolveStrategy = Closure.DELEGATE_ONLY
        paramDescriber.run()
        return parameter
    }

    MutableMethodParameterDesc input(int paramIndex,
                                     @DelegatesTo(MutableMethodParameterDesc) Closure paramDescriber) {
        return redefineParameter(model.inputs, paramIndex, paramDescriber)
    }


    MutableMethodParameterDesc output(int paramIndex,
                                @DelegatesTo(MutableMethodParameterDesc) Closure paramDescriber) {
        return redefineParameter(model.outputs, paramIndex, paramDescriber)
    }

    MutableMethodParameterDesc output(@DelegatesTo(MutableMethodParameterDesc) Closure paramDescriber) {
        return output(0, paramDescriber)
    }

    private MutableMethodParameterDesc redefineParameter(List<IMethodParameterDesc> paramList,
                             int paramIndex,
                             @DelegatesTo(MutableMethodParameterDesc) Closure paramDescriber) {
        if(paramIndex >= paramList.size()) {
            /*
             * Parameter doesnt exists
             */
            throw new IllegalArgumentException("Parameter index exceeds bounds: " + paramIndex + "." +
                " First define the parameter before configuring it.")
        }

        def parameter = MutableMethodParameterDesc.create().from(paramList[paramIndex])

        paramDescriber.delegate = parameter
        paramDescriber.resolveStrategy = Closure.DELEGATE_FIRST
        paramDescriber.run()

        def newParameter = parameter
        paramList.set(paramIndex, newParameter)
        return newParameter
    }


    private MutableJavaReflectionAux java (@DelegatesTo(MutableJavaReflectionAux) Closure javaAuxDescriber) {
        def javaAux = MutableJavaReflectionAux.create()
        if(model.javaAux != null) {
            javaAux.from(model.javaAux)
        }

        javaAuxDescriber.delegate = javaAux
        javaAuxDescriber.resolveStrategy = Closure.DELEGATE_ONLY
        javaAuxDescriber.run()

        def newJavaAux = javaAux
        model.javaAux = newJavaAux
        return newJavaAux
    }

    @Override
    def String getBindingName() {
        "signature"
    }
}
