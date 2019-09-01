package de.upb.sede

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import de.upb.sede.exec.MethodDesc
import de.upb.sede.exec.ServiceDesc
import spock.lang.Specification

class IServiceDescTest extends Specification {

    def "test json integration" () {
        given:
        def serviceDesc = ServiceDesc.builder()
            .qualifier("service.A")
            .addMethods(MethodDesc.builder()
                .qualifier("m1")
                .build())
            .build()
        def mapper = new ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        println(mapper.writeValueAsString(serviceDesc))

        expect:
        serviceDesc.qualifier == "service.A"
        serviceDesc.hashCode() == serviceDesc.hashCode()

        when:
        def jsonData = """
        {
          "methods" : [ {
            "qualifier" : "m1"
          } ],
          "qualifier" : "service.A"
        }
        """
        def serviceDesc1 = mapper.readValue(jsonData, ServiceDesc)
        then:
        serviceDesc1 == serviceDesc
    }

    def "test builders"() {
        def builder = ServiceDesc.builder()

    }
}
