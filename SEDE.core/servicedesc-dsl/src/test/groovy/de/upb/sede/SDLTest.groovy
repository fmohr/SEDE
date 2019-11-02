package de.upb.sede

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import de.upb.sede.exec.IServiceDesc
import de.upb.sede.exec.ServiceDesc
import de.upb.sede.param.IBooleanParameter
import de.upb.sede.param.ICategoryParameter
import de.upb.sede.param.IInterfaceParameter
import de.upb.sede.param.INumericParameter
import de.upb.sede.param.IParameter
import de.upb.sede.param.IParameterDependencyDesc
import de.upb.sede.param.IServiceParameterizationDesc
import de.upb.sede.param.auxiliary.IJavaParameterizationAux
import de.upb.sede.types.IDataTypeDesc
import de.upb.sede.util.FileUtil
import spock.lang.Specification

class SDLTest extends Specification {


    SDLReader reader = new SDLReader()

    static def mapper = new ObjectMapper()
    static  {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    };

    void setup() {
        reader.clearDatabase()
    }

    private final List<IServiceCollectionDesc> readCollectionResource(String resourceFilePrefix) {
        String resourceFilePath = getResourcePath(resourceFilePrefix)
        reader.read(FileUtil.readResourceAsString(resourceFilePath), resourceFilePath)
        return reader.collections
    }

    private final static String getResourcePath(String filePrefix) {
        return "${filePrefix}.servicedesc.groovy"
    }

    def "sdl compile weka"() {
        def wekaCollections = readCollectionResource('weka')
        expect:
        wekaCollections.size() == 1
    }


    def "test services1"() {
        def serviceCollections = readCollectionResource('services1')

        def serviceCollection = serviceCollections[0]

        expect:
        serviceCollection.comments.size() == 1

        serviceCollection.qualifier == "services1"
        serviceCollection.services.size() == 1

        serviceCollection instanceof ServiceCollectionDesc
        serviceCollection.services.stream().allMatch {
            it instanceof ServiceDesc
        }

        when:
        def serviceA = serviceCollection.services[0]
        def typeA = serviceCollection.dataTypes[0]

        then:
        serviceA.qualifier == 'a.A'
        serviceA.isAbstract()
        serviceA.interfaces == ['abstracts.A']

        serviceA.stateType != null
        serviceA.stateType == "a.A"
        serviceA.fieldTypes == ['state' : 'a.A']
        typeA.qualifier == serviceA.qualifier
        typeA.semanticType == 'semantics.A'

        when:
        def constructor = serviceA.methods[0]
        def method1 = serviceA.methods[1]

        then:
        constructor.qualifier == '$construct'
        constructor.signatures[0].inputs.isEmpty()
        constructor.signatures[0].outputs[0].type == "a.A"
        method1.simpleName == "method 1"
        method1.signatures.size() == 4
        method1.signatures.every {it.javaDispatchAux.staticInvocation()}


        when:
        def sig1 = method1.signatures[0]
        then:
        sig1.inputs.size() == 3
        sig1.inputs[0].type == "t1"
        sig1.inputs[1].type == "t2"
        sig1.inputs[2].type == "t3"
        sig1.outputs.size() == 2
        sig1.outputs[0].type == "t4"

        when:
        def sig3 = method1.signatures[2]
        then:
        sig3.inputs[1].type == "t2"
        sig3.inputs[1].name == null
        !sig3.inputs[1].callByValue()
        sig3.inputs[1].fixedValue == "SOME_FIXED_VALUE"

        when:
        def sig4 = method1.signatures[3]
        then:
        sig4.inputs[0].name == "First Input Parameter"
        !sig4.inputs[1].callByValue()
        sig4.outputs[0].name == "Return Value"
        sig4.inputs[0].type == "t1"
        sig4.inputs[1].type == "t2"
        sig4.outputs[0].type == "t3"

        when:
        def method2 = serviceA.methods[2]
        def m2sig1 = method2.signatures[0]
        then:
        method2.signatures.size() == 1
        method2.qualifier == "m2"
        m2sig1.inputs[0].type == "t1"
        m2sig1.inputs[0].name == "First Input Parameter of m2"
    }


    def "test parameterized-services"() {
        List<ServiceCollectionDesc> serviceCollections = readCollectionResource('parameterized-services')
        ServiceCollectionDesc serviceCollection1 = serviceCollections.find { it.qualifier == 'service.Collection.1' }

        expect:
        serviceCollection1 != null

        when:
        IServiceDesc service1 = serviceCollection1.services.find {it.qualifier == 'service1'}
        def params = service1.serviceParameters
        List<IBooleanParameter> boolParams = params.parameters.grep(IBooleanParameter)
        then:
        boolParams.every { it.qualifier == 'bParam' }
        boolParams.every { it.defaultValue == true }
        boolParams.every { it.isOptional() == true }

        when:
        List<INumericParameter> numParams = params.parameters.grep(INumericParameter)
        then:
        numParams.every {it.qualifier == 'nParam'}
        numParams.every {it.defaultValue == 10.0 }
        numParams.every {it.isInteger()}
        numParams.every {it.min == 0.5}
        numParams.every {it.max == 20.5}
        numParams.every {it.splitsRefined == 4}
        numParams.every {it.minInterval == 10}
        numParams.every {it.isOptional()}
        when:
        List<ICategoryParameter> catParams = params.parameters.grep(ICategoryParameter)
        then:
        catParams.every {it.qualifier == 'cParam'}
        catParams.every {it.defaultValue == 'A'}
        catParams.every {it.categories == ['A', 'B', 'C'] }
        catParams.every {it.isOptional() }

        when:
        List<IInterfaceParameter> intParams = params.parameters.grep(IInterfaceParameter)
        then:
        intParams.every {it.qualifier == 'iParam'}
        intParams.every {it.defaultValue == null}
        intParams.every {it.interfaceQualifier == 'interface.I1'}
        intParams.every {it.isOptional() }

        when:
        List<IParameterDependencyDesc> dependencies = params.parameterDependencies
        then:
        dependencies.size() == 3
        dependencies.every({it.premise == 'b2 in {true}'})
        dependencies.every {it.conclusion == 'b3 in {false}'}

        when:
        def javaAux = params.getJavaParameterizationAuxiliaries()

        then:
        javaAux.parameterHandler.className() == 'Service1ParameterHandler'
        javaAux.autoScanEachParam == true
        javaAux.bundleInArray == true
        javaAux.bundleInMap == true
        javaAux.bundleInList == true
        javaAux.precedeParamsWithNames == true
        javaAux.paramOrder == ['b3', 'b2', 'b1']
    }

    def "test auxiliaries"() {
        List<ServiceCollectionDesc> serviceCollections = readCollectionResource('auxiliaries')
        ServiceCollectionDesc c1 = serviceCollections.find { it.qualifier == 'C1' }

        expect:
        c1 != null

        when:
        IServiceDesc s1 = c1.services.find {it.qualifier == 'S1'}
        then:
        s1.javaDispatchAux != null
        s1.javaDispatchAux.staticInvocation() == true

        when:
        IServiceParameterizationDesc params = s1.serviceParameters
        IJavaParameterizationAux paramAux = params.javaParameterizationAuxiliaries
        then:
        paramAux.paramOrder == ['1', '2', '3']

        when:
        IDataTypeDesc t2 = c1.dataTypes.find {it.qualifier == 't2'}
        then:
        t2.javaTypeAux.dataCastHandler != null
        t2.javaTypeAux.dataCastHandler.className() == 'org.example.T2Handler'
    }
}
