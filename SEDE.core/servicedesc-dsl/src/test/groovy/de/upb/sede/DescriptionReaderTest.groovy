package de.upb.sede

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

import de.upb.sede.exec.ServiceDesc
import spock.lang.Specification

class DescriptionReaderTest extends Specification {


    DescriptionReader reader = new DescriptionReader()

    static def mapper = new ObjectMapper()
    static  {
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    };


    def "test read services1"() {
        def serviceCollections = reader.read(new File("service-descriptions/services1.servicedesc.groovy"))
        println(mapper.writeValueAsString(serviceCollections))

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

        serviceA.stateType.isPresent()
        serviceA.stateType.get() == "a.A"
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
        method1.signatures.every {it.javaAux.staticInvocation()}


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
}
