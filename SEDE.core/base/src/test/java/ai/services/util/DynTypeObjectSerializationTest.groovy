package de.upb.sede.util

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class DynTypeObjectSerializationTest extends Specification {

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
        def kneadable = DynRecord.fromJson(jsonData)
        Container container = kneadable.cast(Container)

        then:
        kneadable.number("third") == 3

        container.first.cast(A).a == 1
        container.first.cast(B).b == "one"
        container.second.cast(A).a == 2
        container.second.cast(B).b == "two"

        container.first.cast(C).c[0].cast(A).a == 1

        container.second.cast(C).c[1].cast(B).b == "two"

        when:
        def mapper = new ObjectMapper()
        jsonData = mapper.writeValueAsString(container)
//        print(jsonData)
        kneadable = mapper.readValue(jsonData, DynRecord)

        container = kneadable.cast(Container)

        then:

        container.first.cast(A).a == 1
        container.first.cast(B).b == "one"
        container.second.cast(A).a == 2
        container.second.cast(B).b == "two"

        container.first.cast(C).c[0].cast(A).a == 1

        container.second.cast(C).c[1].cast(B).b == "two"

        when:
        kneadable.number("third")
        then:
        thrown(IllegalArgumentException)

    }


    static class Container {
        DynTypeObject first
        DynTypeObject second
    }

    static class A {
        int a
    }

    static class B {
        String b
    }

    static class C {
        List<DynTypeObject> c
    }

}
