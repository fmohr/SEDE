package ai.services

import ai.services.exec.IMethodParameterDesc
import ai.services.exec.MutableMethodDesc
import ai.services.exec.MutableMethodParameterDesc
import ai.services.util.DynRecord
import groovy.transform.PackageScope

class MethodDomain
    extends DomainAware<MutableMethodDesc, ServiceDomain> {

    // model MutableMethodDesc

    // topDom ServiceDomain

    static void aux(MutableMethodDesc model, @DelegatesTo(DynRecord) desc) {
        Shared.aux(model, desc)
    }

    static MutableMethodDesc comment(MutableMethodDesc model, String ... comments) {
        Shared.comment(model, comments)
        return model
    }

    static MutableMethodDesc setInfo(MutableMethodDesc model, String commentBlock) {
        Shared.setInfo(model, commentBlock)
        return model
    }


    static MutableMethodParameterDesc input(MutableMethodDesc model, int paramIndex,
                                     @DelegatesTo(MutableMethodParameterDesc) Closure paramDescriber) {
        return redefineParameter(model, model.inputs, paramIndex, paramDescriber)
    }

    static MutableMethodParameterDesc output(MutableMethodDesc model, int paramIndex,
                                      @DelegatesTo(MutableMethodParameterDesc) Closure paramDescriber) {
        return redefineParameter(model, model.outputs, paramIndex, paramDescriber)
    }

    static MutableMethodParameterDesc output(MutableMethodDesc model, @DelegatesTo(MutableMethodParameterDesc) Closure paramDescriber) {
        return output(model, 0, paramDescriber)
    }

    private static MutableMethodParameterDesc redefineParameter(MutableMethodDesc model, List<IMethodParameterDesc> paramList,
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


    static void setInputTypes(MutableMethodDesc model, String... inputTypes) {
        model.inputs.clear()
        addInputTypes(model, inputTypes)
    }

    static void addInputTypes(MutableMethodDesc model, String... inputTypes) {
        for(String inputType: inputTypes) {
            model.inputs += param {
                type = inputType
            }
        }
    }

    static void setOutputTypes(MutableMethodDesc model, String... outputTypes) {
        model.outputs.clear()
        addOutputTypes(model, outputTypes)
    }

    static void addOutputTypes(MutableMethodDesc model, String... outputTypes) {
        for(String outputType: outputTypes) {
            model.outputs += param {
                type = outputType
            }
        }
    }

    static void addInput(MutableMethodDesc model, String type, String name) {
        String t = type
        String n = name
        model.inputs += param model, [type: type, name: name]
    }

    static void addOutput(MutableMethodDesc model, String type, String name) {
        model.outputs += param model, [type: type, name: name]
    }

    static MutableMethodParameterDesc param(MutableMethodDesc model, String t, String n) {
        return param {
            type = t
            name = n
        }
    }

    static MutableMethodParameterDesc param(MutableMethodDesc model, Map<String, String> paramDesc) {
        return param(model, paramDesc["type"], paramDesc["name"])
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


}
