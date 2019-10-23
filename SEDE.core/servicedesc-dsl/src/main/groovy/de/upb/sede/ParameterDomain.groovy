package de.upb.sede

import de.upb.sede.param.IParameter
import de.upb.sede.param.MutableBooleanParameter
import de.upb.sede.param.MutableCategoryParameter
import de.upb.sede.param.MutableNumericParameter
import de.upb.sede.param.MutableParameterDependencyDesc
import de.upb.sede.param.MutableServiceParameterizationDesc
import de.upb.sede.param.auxiliary.JavaParameterizationAux
import de.upb.sede.param.auxiliary.MutableJavaParameterizationAux

class ParameterDomain
    extends DomainAware<MutableServiceParameterizationDesc, ServiceDomain>
    implements Shared.CommentAware, Shared.AuxAware<MutableJavaParameterizationAux> {


    private void readDescription(Object parameter, Closure paramDescriber) {
        paramDescriber.delegate = parameter
        paramDescriber.resolveStrategy = Closure.DELEGATE_FIRST
        paramDescriber.run()
    }

    private final static Closure PARAM_QUALIFIER_SETTER = { Map paramDef, parameter ->
        if(! "name" in paramDef) {
            throw new IllegalArgumentException("Parameter description doesn't define qualifier with 'name'" + paramDef.toString())
        }
        def name = paramDef['name']
        if(!(name instanceof String)){
            throw new IllegalArgumentException("Parameter description badly typed. 'name' is not a String: " + name)
        }
        parameter.qualifier = name
    }

    private final static Closure PARAM_OPTIONAL_SETTER = { Map paramDef, parameter->
        if("optional" in paramDef) {
            def opt = paramDef['optional']
            if (!(opt instanceof Boolean)) {
                throw new IllegalArgumentException("Parameter description badly typed. 'optional' is not a boolean: " + opt)
            }
            parameter.isOptional = opt
        }
    }



    MutableBooleanParameter bool(@DelegatesTo(MutableBooleanParameter) Closure paramDescriber) {
        def boolParam = MutableBooleanParameter.create()
        readDescription(boolParam, paramDescriber)
        model.parameters.add(boolParam)
        return boolParam
    }

    MutableBooleanParameter bool(String name, boolean defaultVal = false, boolean opt = false) {
        return bool {
            qualifier = name
            isOptional = opt
            defaultValue = defaultVal
        }
    }

    MutableBooleanParameter bool(Map paramDef) {
        def boolParam = bool {
            if ('default' in paramDef) {
                defaultValue = paramDef['default']
            }
        }
        PARAM_QUALIFIER_SETTER(paramDef, boolParam)
        PARAM_OPTIONAL_SETTER(paramDef, boolParam)
        return boolParam
    }

    MutableNumericParameter numeric(@DelegatesTo(MutableNumericParameter) Closure description) {
        def numbParam = MutableNumericParameter.create()
        readDescription(numbParam, description)
        model.parameters.add(numbParam)
        return numbParam
    }

    MutableNumericParameter numeric(String name, double defaultVal, Double minParam = null, Double maxParam = null, boolean isIntegerParam = false, Integer refineSplitsParam = null, Integer minIntervalParam = null,  boolean opt = false) {
        return numeric {
            qualifier = name
            defaultValue = defaultVal
            min = minParam
            max = maxParam
            splitsRefined = refineSplitsParam
            minInterval = minIntervalParam
            isInteger = isIntegerParam
            isOptional = opt
        }
    }

    MutableNumericParameter numeric(Map paramDef) {
        def num = numeric {
            min = paramDef.getOrDefault('min', null)
            max = paramDef.getOrDefault('max', null)
            defaultValue = paramDef.getOrDefault('default', null)
            splitsRefined = paramDef.getOrDefault ('splitsRefined', null)
            minInterval = paramDef.getOrDefault('minInterval', null)
            isInteger = paramDef.getOrDefault('isInteger', false)
        }
        PARAM_QUALIFIER_SETTER(paramDef, num)
        PARAM_OPTIONAL_SETTER(paramDef, num)
        return num
    }

    MutableCategoryParameter category(@DelegatesTo(MutableCategoryParameter) Closure describer) {
        def cat = MutableCategoryParameter.create()
        readDescription(cat, describer)
        model.parameters.add(cat)
        return cat
    }

    MutableCategoryParameter category(String name, String defaultVal, List<String> categoriesParam, boolean opt) {
        return category {
            qualifier = name
            defaultValue = defaultVal
            categories = categoriesParam
            isOptional = opt
        }
    }

    MutableCategoryParameter category(Map catDef) {
        def cat =  category {
            defaultValue = catDef.getOrDefault('default', null)
            if('categories' in catDef) {
                categories = catDef['categories']
            }
        }
        PARAM_QUALIFIER_SETTER(catDef, cat)
        PARAM_OPTIONAL_SETTER(catDef, cat)
        return cat
    }

    MutableJavaParameterizationAux java(@DelegatesTo(MutableJavaParameterizationAux) Closure auxDescriber) {
        def javaAux = model.javaParameterizationAuxiliaries
        if(javaAux == null) {
            model.javaParameterizationAuxiliaries = MutableJavaParameterizationAux.create()
            javaAux = model.javaParameterizationAuxiliaries
        }
        readDescription(javaAux, auxDescriber)
        return javaAux
    }

    MutableParameterDependencyDesc dependency(@DelegatesTo(MutableParameterDependencyDesc) Closure describer) {
        def dependencyDesc = MutableParameterDependencyDesc.create().setPremise("").setConclusion("")
        readDescription(dependencyDesc, describer)
        return dependencyDesc
    }

    MutableParameterDependencyDesc dependency(String pre, String con) {
        return dependency {
            premise = pre
            conclusion = con
        }
    }

    MutableParameterDependencyDesc dependency(Map dependencyDef) {
        return dependency {
            def pre = null
            if('pre' in dependencyDef) {
                pre = dependencyDef['pre']
            }
            if('premise' in dependencyDef) {
                pre = dependencyDef['premise']
            }
            if(!(pre instanceof String)) {
                throw new IllegalArgumentException("Dependency definition ill typed. Premise: " + pre)
            }
            def con = null
            if('con' in dependencyDef) {
                con = dependencyDef['con']
            }
            if('conclusion' in dependencyDef) {
                con = dependencyDef['conclusion']
            }
            if(!(pre instanceof String)) {
                throw new IllegalArgumentException("Dependency definition ill typed. Premise: " + pre)
            }
            if(!(con instanceof String)) {
                throw new IllegalArgumentException("Dependency definition ill typed. Conclusion: " + con)
            }
            return dependency {
                premise = pre
                conclusion = con
            }
        }
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
