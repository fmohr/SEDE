package de.upb.sede.util

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.util.stream.Collectors

class KneadableJsonObjectSerializationTest extends Specification {

    def "test kneadjsonobject serialization" () {
        given:
        def jsonData = """
        {
            "first" : {
                "a" : 1,
                "b" : "one",
                "c" : [{
                    "a" : 1
                }]
            },
            "second" : {
                "a" : 2,
                "b" : "two",
                "c" : [{}, {
                    "b" : "two"
                }]
            },
            "third" : 3
        }
        """
        when:
        def kneadable = KneadableJsonObject.fromJson(jsonData)
        Container container = kneadable.knead(Container)

        then:
        kneadable.knibbleNumber("third") == 3

        container.first.knead(A).a == 1
        container.first.knead(B).b == "one"
        container.second.knead(A).a == 2
        container.second.knead(B).b == "two"

        container.first.knead(C).c[0].knead(A).a == 1

        container.second.knead(C).c[1].knead(B).b == "two"

        when:
        def mapper = new ObjectMapper()
        jsonData = mapper.writeValueAsString(container)
//        print(jsonData)
        kneadable = mapper.readValue(jsonData, Kneadable)

        container = kneadable.knead(Container)

        then:

        container.first.knead(A).a == 1
        container.first.knead(B).b == "one"
        container.second.knead(A).a == 2
        container.second.knead(B).b == "two"

        container.first.knead(C).c[0].knead(A).a == 1

        container.second.knead(C).c[1].knead(B).b == "two"

        when:
        kneadable.knibbleNumber("third")
        then:
        thrown(IllegalArgumentException)

    }


    static class Container {
        Kneadable first
        Kneadable second
    }

    static class A {
        int a
    }

    static class B {
        String b
    }

    static class C {
        List<Kneadable> c
    }

}
