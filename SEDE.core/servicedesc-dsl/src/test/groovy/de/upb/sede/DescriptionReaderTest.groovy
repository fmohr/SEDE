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

        def serviceCollection = reader.read(new File("service-descriptions/services1.servicedesc.groovy"))["services1"]
        println(mapper.writeValueAsString(serviceCollection))


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

        serviceA.fieldTypes == ['state' : 'a.A']
        typeA.qualifier == serviceA.qualifier
        typeA.semanticType == 'semantics.A'

        serviceA.methods.size() == 1

        when:
        def method1 = serviceA.methods[0]

        then:
        method1.simpleName == "method 1"

    }
}
