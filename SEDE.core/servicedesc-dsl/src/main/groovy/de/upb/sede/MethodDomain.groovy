package de.upb.sede

import de.upb.sede.exec.IMethodParameterDesc
import de.upb.sede.exec.MethodParameterDesc
import de.upb.sede.exec.MutableMethodDesc
import de.upb.sede.exec.MutableMethodParameterDesc
import groovy.transform.NamedVariant
import groovy.transform.PackageScope

class MethodDomain
    extends DomainAware<MutableMethodDesc, ServiceDomain>
    implements Shared.CommentAware,
        Shared.AuxDomAware  {

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

    @PackageScope
    static MutableMethodDesc createMethod(String methodQualifier, Map methodDefs) {
        def newMethod = MutableMethodDesc.create()
        newMethod.qualifier = methodQualifier

        def inputParamReader = (this.&readSignatureParam).curry(newMethod, methodDefs, true)
        if('inputs' in methodDefs)
            inputParamReader("inputs")
        else if('ins' in methodDefs)
            inputParamReader('ins')
        else if('input' in methodDefs)
            inputParamReader('input')

        def outputParamReader = (this.&readSignatureParam).curry(newMethod, methodDefs, false)
        if('outputs' in methodDefs)
            outputParamReader("outputs")
        else if('outs' in methodDefs)
            outputParamReader('outs')
        else if('output' in methodDefs)
            outputParamReader('output')

        if('static' in methodDefs && methodDefs['static'] == true)
            newMethod.setIsContextFree(true)

        return newMethod
    }

    private static void readSignatureParam(MutableMethodDesc method, Map types, boolean isInput, String key) {
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

        (isInput ? method.inputs : method.outputs).addAll(newParams)
    }


    @Override
    def String getBindingName() {
        return "method"
    }
}
