package de.upb.sede

import de.upb.sede.param.IParameter
import de.upb.sede.param.MutableBooleanParameter
import de.upb.sede.param.MutableParameterDependencyDesc
import de.upb.sede.param.MutableServiceParameterizationDesc
import de.upb.sede.param.auxiliary.JavaParameterizationAux
import de.upb.sede.param.auxiliary.MutableJavaParameterizationAux

class ParameterDomain
    extends DomainAware<MutableServiceParameterizationDesc, ServiceDomain>
    implements Shared.CommentAware, Shared.AuxAware<MutableJavaParameterizationAux> {


    private void readDescription(IParameter parameter, Closure paramDescriber) {
        paramDescriber.delegate = parameter
        paramDescriber.resolveStrategy = Closure.DELEGATE_FIRST
        paramDescriber.run()
    }

    MutableBooleanParameter bool(@DelegatesTo(MutableBooleanParameter) Closure paramDescriber) {
        def boolParam = MutableBooleanParameter.create()
        readDescription(boolParam, paramDescriber)
        model.parameters.add(boolParam)
        return boolParam
    }

    MutableBooleanParameter bool(String name, boolean defaultVal) {
        return bool {
            qualifier = name
            defaultValue = defaultVal
        }
    }

    MutableBooleanParameter bool(Map paramDef) {
        return bool {
            qualifier = extractParamName(paramDef)
            if ('default' in paramDef) {
                defaultValue = paramDef['default']
            }
        }
    }

    MutableJavaParameterizationAux java(@DelegatesTo(MutableJavaParameterizationAux) Closure auxDescriber) {
        def javaAux = model.javaParameterizationAuxiliaries
        if(javaAux == null) {
            model.javaParameterizationAuxiliaries = MutableJavaParameterizationAux.create()
            javaAux = model.javaParameterizationAuxiliaries
        }
        auxDescriber.delegate = javaAux
        auxDescriber.resolveStrategy = Closure.DELEGATE_ONLY
        auxDescriber.run()
        return javaAux
    }

    MutableParameterDependencyDesc paramDependency(@DelegatesTo(MutableParameterDependencyDesc) Closure dependencyDescription) {

    }

    @Override
    def String getBindingName() {
        return 'params'
    }

    private String extractParamName(Map paramDef) {
        if(! "name" in paramDef) {
            throw new RuntimeException("Provided boolean parameter declaration needs to define parameter name. Provided param definition: " + paramDef.toString())
        }

        String paramName = paramDef["name"] as String
        return paramName;
    }

    @Override
    MutableJavaParameterizationAux setJavaAux(MutableJavaParameterizationAux javaAux) {
        model.javaParameterizationAuxiliaries = javaAux
        return javaAux
    }


    MutableJavaParameterizationAux setJavaParamAux(MutableJavaParameterizationAux javaAux) {
        model.javaParameterizationAuxiliaries = javaAux
        return javaAux
    }

    @Override
    MutableJavaParameterizationAux getJavaAux() {
        if(model.javaParameterizationAuxiliaries == null)
            return setJavaAux(MutableJavaParameterizationAux.create())
        else
            return model.javaParameterizationAuxiliaries as MutableJavaParameterizationAux
    }
}
