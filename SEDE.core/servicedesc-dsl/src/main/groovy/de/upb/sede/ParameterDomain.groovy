package de.upb.sede

import de.upb.sede.param.IParameter
import de.upb.sede.param.MutableBooleanParameter
import de.upb.sede.param.MutableCategoryParameter
import de.upb.sede.param.MutableInterfaceParameter
import de.upb.sede.param.MutableNumericParameter
import de.upb.sede.param.MutableParameterDependencyDesc
import de.upb.sede.param.MutableServiceParameterizationDesc
import de.upb.sede.param.auxiliary.JavaParameterizationAux
import de.upb.sede.param.auxiliary.MutableJavaParameterizationAux
import de.upb.sede.types.MutableDataTypeDesc
import de.upb.sede.util.DynRecord

class ParameterDomain
    extends DomainAware<MutableServiceParameterizationDesc, ServiceDomain> {


    // model MutableServiceParameterizationDesc

    // topDom ServiceDomain

    static MutableServiceParameterizationDesc aux(MutableServiceParameterizationDesc model, @DelegatesTo(DynRecord) Closure desc) {
        Shared.aux(model, desc)
        return model
    }

    private static void readDescription(Object parameter, Closure paramDescriber) {
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



    static MutableBooleanParameter bool(MutableServiceParameterizationDesc model, @DelegatesTo(MutableBooleanParameter) Closure paramDescriber) {
        def boolParam = MutableBooleanParameter.create()
        readDescription(boolParam, paramDescriber)
        model.parameters.add(boolParam)
        return boolParam
    }

    static MutableBooleanParameter bool(MutableServiceParameterizationDesc model, String name, boolean defaultVal = false, boolean opt = false) {
        return bool (model) {
            qualifier = name
            isOptional = opt
            defaultValue = defaultVal
        }
    }

    static MutableBooleanParameter bool(MutableServiceParameterizationDesc model, Map paramDef) {
        def boolParam = bool (model) {
            if ('default' in paramDef) {
                defaultValue = paramDef['default']
            }
        }
        PARAM_QUALIFIER_SETTER(paramDef, boolParam)
        PARAM_OPTIONAL_SETTER(paramDef, boolParam)
        return boolParam
    }

    static MutableInterfaceParameter requiredInterface(MutableServiceParameterizationDesc model, @DelegatesTo(MutableInterfaceParameter) Closure paramDescriber) {
        def intfaceParam = MutableInterfaceParameter.create()
        readDescription(intfaceParam, paramDescriber)
        model.parameters.add(intfaceParam)
        return intfaceParam
    }

    static MutableInterfaceParameter requiredInterface(MutableServiceParameterizationDesc model, String name, String intfaceQualifier, boolean opt = false) {
        return requiredInterface(model) {
            qualifier = name
            isOptional = opt
            interfaceQualifier = intfaceQualifier
        }
    }

    static MutableInterfaceParameter requiredInterface(MutableServiceParameterizationDesc model, Map paramDef) {
        def intfaceParam = requiredInterface(model)  {
            if ('interfaceQualifier' in paramDef) {
                interfaceQualifier = paramDef['interfaceQualifier']
            }
        }
        PARAM_QUALIFIER_SETTER(paramDef, intfaceParam)
        PARAM_OPTIONAL_SETTER(paramDef, intfaceParam)

        if(!intfaceParam.isInitialized()) {
            throw new IllegalArgumentException("Interface definition '${intfaceParam.qualifier}' does not define the requested interface qualifier.");
        }
        return intfaceParam
    }

    static MutableInterfaceParameter requiredInterface(MutableServiceParameterizationDesc model, String intface) {
        return requiredInterface(model)  {
            qualifier = intface
            isOptional = false
            interfaceQualifier = intface
        };
    }

    static void requiredInterfaces(MutableServiceParameterizationDesc model, String... interfaceQualifiers) {
        for(String interfaceQualifier : Objects.requireNonNull(interfaceQualifiers, "No interface qualifiers provided.")){
            requiredInterface(model, interfaceQualifier)
        }
    }

    static MutableNumericParameter numeric(MutableServiceParameterizationDesc model, @DelegatesTo(MutableNumericParameter) Closure description) {
        def numbParam = MutableNumericParameter.create()
        readDescription(numbParam, description)
        model.parameters.add(numbParam)
        return numbParam
    }

    static MutableNumericParameter numeric(MutableServiceParameterizationDesc model, String name, double defaultVal, Double minParam = null, Double maxParam = null, boolean isIntegerParam = false, Integer refineSplitsParam = null, Integer minIntervalParam = null,  boolean opt = false) {
        return numeric(model)  {
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

    static MutableNumericParameter numeric(MutableServiceParameterizationDesc model, Map paramDef) {
        def num = numeric(model)  {
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

    static MutableCategoryParameter category(MutableServiceParameterizationDesc model, @DelegatesTo(MutableCategoryParameter) Closure describer) {
        def cat = MutableCategoryParameter.create()
        readDescription(cat, describer)
        model.parameters.add(cat)
        return cat
    }

    static MutableCategoryParameter category(MutableServiceParameterizationDesc model, String name, String defaultVal, List<String> categoriesParam, boolean opt) {
        return category(model)  {
            qualifier = name
            defaultValue = defaultVal
            categories = categoriesParam
            isOptional = opt
        }
    }

    static MutableCategoryParameter category(MutableServiceParameterizationDesc model, Map catDef) {
        def cat =  category(model)  {
            defaultValue = catDef.getOrDefault('default', null)
            if('categories' in catDef) {
                categories = catDef['categories']
            }
        }
        PARAM_QUALIFIER_SETTER(catDef, cat)
        PARAM_OPTIONAL_SETTER(catDef, cat)
        return cat
    }

    static MutableParameterDependencyDesc dependency(MutableServiceParameterizationDesc model, @DelegatesTo(MutableParameterDependencyDesc) Closure describer) {
        def dependencyDesc = MutableParameterDependencyDesc.create().setPremise("").setConclusion("")
        readDescription(dependencyDesc, describer)
        model.parameterDependencies += dependencyDesc
        return dependencyDesc
    }

    static MutableParameterDependencyDesc dependency(MutableServiceParameterizationDesc model, String pre, String con) {
        return dependency(model) {
            premise = pre
            conclusion = con
        }
    }

    static MutableParameterDependencyDesc dependency(MutableServiceParameterizationDesc model, Map dependencyDef) {
        return dependency(model) {
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
            premise = pre
            conclusion = con
        }
    }



    private static String extractParamName(Map paramDef) {
        if(! "name" in paramDef) {
            throw new RuntimeException("Provided boolean parameter declaration needs to define parameter name. Provided param definition: " + paramDef.toString())
        }

        String paramName = paramDef["name"] as String
        return paramName;
    }
}
