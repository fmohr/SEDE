package ai.services


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import ai.services.exec.IServiceDesc
import ai.services.exec.ServiceDesc
import ai.services.param.IBooleanParameter
import ai.services.param.ICategoryParameter
import ai.services.param.IInterfaceParameter
import ai.services.param.INumericParameter
import ai.services.param.IParameterDependencyDesc
import ai.services.param.IServiceParameterizationDesc
import ai.services.exec.auxiliary.IJavaParameterizationAux
import ai.services.types.IDataTypeDesc
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
        reader.readResource(getResourcePath(resourceFilePrefix))
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

        then:
        serviceA.qualifier == 'a.A'
        serviceA.isAbstract()
        serviceA.interfaces == ['abstracts.A']

        serviceA.stateType != null
        serviceA.stateType == "a.A"
        serviceA.fieldTypes == ['state' : 'a.A']
        serviceCollection.dataTypes.isEmpty()

        when:
        def constructor = serviceA.methods[0]

        then:
        constructor.qualifier == '__construct'
        constructor.inputs.isEmpty()
        constructor.outputs[0].type == "a.A"

        when:
        def method2 = serviceA.methods[1]
        then:
        method2.qualifier == "m2"
        method2.inputs[0].type == "t1"
        method2.inputs[0].name == "First Input Parameter of m2"
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
        def aux = params.dynAux
        def javaAux = aux.cast(JavaParameterizationAux)

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
        s1.dynAux != null
        s1.dynAux.cast(ai.services.exec.auxiliary.JavaDispatchAux).staticInvocation() == true

        when:
        IServiceParameterizationDesc params = s1.serviceParameters
        IJavaParameterizationAux paramAux = params.dynAux.cast(JavaParameterizationAux)

        then:
        paramAux.paramOrder == ['1', '2', '3']

        when:
        IDataTypeDesc t2 = c1.dataTypes.find {it.qualifier == 't2'}
        then:
        t2.dynAux.cast(JavaTypeAux).dataCastHandler != null
        t2.dynAux.cast(JavaTypeAux).dataCastHandler.className() == 'org.example.T2Handler'
    }
}
