package de.upb.sede.util

import com.fasterxml.jackson.databind.ObjectMapper
import groovyjarjarasm.asm.tree.InsnList
import spock.lang.Specification


class KneadableWobblyField extends Specification {

    def "simple node test"() {
        given:
        def jsonData = """
        {
            "a" : "test",
            "b" : 12
        }
        """
        when:
        Kneadable kneadable = KneadableJsonObject.fromJson(jsonData)
        A a = kneadable.knead(A)
        B b = kneadable.knead(B)
        then:
        a.a.isPresent()
        b.b.isPresent()
        a.a.get() == "test"
        b.b.get() == 12

        when:
        b.b.set(13)
        then:
        b.knibbleNumber("b") == 12
        b.b.get() == 13
        new ObjectMapper().convertValue(b, Map) == ["b" : 13]
        new ObjectMapper().convertValue(a, Map) == ["a" : "test"]
    }
    def "object node test" () {
        given:
        def jsonData = """
          {
             "a" : { "a" : "test" }
             "b" : { "b" : 11 }
          }
        """
        when:
        Kneadable kneadable = KneadableJsonObject.fromJson(jsonData)
        AB ab = kneadable.knead(AB)
        then:
        ab.a instanceof  WobblyField
        ab.b instanceof  WobblyField
        ab.a.isPresent()
        ab.b.isPresent()
        ab.a.get().a.get() == "test"
        ab.b.get().b.get() == 11
        when:
        def serialization = new ObjectMapper().convertValue(ab, Map)
        then:
        serialization == [a:["a" : "test"], b:["b" : 11]]
    }

    def "knead field test" () {
        given:
        def jsonData = """
          {
             "field" : { "a" : "test", "b" : 1}
          }
        """
        when:
        Kneadable kneadable = KneadableJsonObject.fromJson(jsonData)
        def kneadAB = kneadable.knead(KneadAB)
        then:
        kneadAB.field.isPresent()
        kneadAB.field.get().knead(A).a.get() == "test"
        kneadAB.field.get().knead(B).b.get() == 1

        when:
        def serialization = new ObjectMapper().convertValue(kneadable, Map)
        then:
        serialization == [field : [a : "test", b : 1]]
    }

    static class A {
//       {
//          "a" : "test"
//       }
        WobblyField<String> a;
    }
    static class B extends KneadForm{
//        {
//          "b" : 12
//        }
        MutableWobblyField<Integer> b;
    }
    static class AB {
//      {
//         "a" : { "a" : "test" }
//         "b" : { "b" : 11 }
//      }
        WobblyField<A> a;
        WobblyField<B> b;
    }
    static class KneadAB {
//      {
//         "field" : { "a" : "test", "b" : 1}
//      }
        WobblyField<KneadableJsonObject> field;
    }
}
