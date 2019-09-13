package de.upb.sede

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.jsonSchema.JsonSchema
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator
import de.upb.sede.exec.MethodDesc
import de.upb.sede.exec.MutableServiceDesc
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

    def "test modifiables"() {
        def s1 = MutableServiceDesc.create()
        s1.qualifier = "a.B"
        def mapper = new ObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        println(mapper.writeValueAsString(s1))

        expect:
        true
    }

    def "test json schema"() {
        // TODO write a gradle task that generates a json schema and writes it as a file.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
// configure mapper, if necessary, then create schema generator
        JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
        JsonSchema schema = schemaGen.generateSchema(ServiceCollectionDesc.class);

        println(mapper.writeValueAsString(schema))
        expect:
        true
    }
}
