package de.upb.sede.util

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class DynTypeFieldTest extends Specification {

    def "test basics" () {
        given:
        def jsonData = """
        {
            "a" : 1,
            "b" : "test"
        }
        """
        when:
        DynTypeField field = new ObjectMapper().readValue(jsonData, DynTypeField)
        then:
        field.cast(A).a == 1
        field.cast(B).b == "test"

        when:
        def A1 = field.cast(A)
        def A2 = field.cast(A)
        then:
        A1.is(A2)
        when:
        A1.a++
        then:
        A2.a == 2
        when:
        def map = new ObjectMapper().convertValue(field, Map)
        then:
        map == [a: 2, b : "test"]
    }


    def "test missing entries" () {
        given:
        def jsonData = """
        {
            "a" : 1
        }
        """
        when:
        DynTypeField field = new ObjectMapper().readValue(jsonData, DynTypeField)
        def ab = field.cast(AB)
        then:
        ab.a == 1
        ab.b == "default"

        when:
        def map = new ObjectMapper().convertValue(field, Map)
        then:
        map == [a: 1, b: "default"]

        when:
        ab.b = "new val"
        map = new ObjectMapper().convertValue(field, Map)
        then:
        map == [a: 1, b: "new val"]
    }

    static class A {
        int a
    }

    static class B {
        String b
    }

    static class AB{
        String b = "default"
        int a
    }


}
