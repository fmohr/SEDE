package de.upb.sede.util

import com.fasterxml.jackson.databind.ObjectMapper
import javafx.beans.binding.When
import spock.lang.Specification

class KneadableFieldTest extends Specification {

    def "test fields" () {
        given:
        def jsonData = """
        {
            "a" : 1,
            "b" : "test"
        }
        """
        when:
        KneadableField field = new ObjectMapper().readValue(jsonData, KneadableField)
        then:
        field.knead(A).a == 1
        field.knead(B).b == "test"

        when:
        def A1 = field.knead(A)
        def A2 = field.knead(A)
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

    def "test kneadform" () {
        given:
        def jsonData = """
        {
            "a" : 1,
            "b" : "test"
        }
        """
        when:
        KneadableField field = new ObjectMapper().readValue(jsonData, KneadableField)
        def AView = field.knead(AView)
        then:
        AView.a == 1
        AView.source.get() == field
        when:
        AView.setA(3)
        then:
        AView.a == 3
        when:
        AView.knead(B).b = "changed"
        then:
        AView.knead(B).b == "changed"
        when:
        def map = new ObjectMapper().convertValue(field, Map)
        then:
        map == [a: 3, b : "changed"]
    }

    def "test completer" () {
        given:
        def jsonData = """
        {
            "a" : 1
        }
        """
        when:
        KneadableField field = new ObjectMapper().readValue(jsonData, KneadableField)
        def ab = field.knead(AB)
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

    static class AView extends KneadForm  {

        static final String a_FIELD = "a"

        def getA() {
            return super.knibbleNumber(a_FIELD).intValue()
        }

        def setA(int a) {
            return super.setField(a_FIELD, a)
        }
    }

}
