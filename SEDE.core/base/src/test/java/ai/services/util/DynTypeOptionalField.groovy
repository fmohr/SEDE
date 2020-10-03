package ai.services.util

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification


class DynTypeOptionalField extends Specification {

    def "simple node test"() {
        given:
        def jsonData = """
        {
            "a" : "test",
            "b" : 12
        }
        """
        when:
        DynType kneadable = DynRecord.fromJson(jsonData)
        A a = kneadable.cast(A)
        B b = kneadable.cast(B)
        then:
        a.a.isPresent()
        b.b.isPresent()
        a.a.get() == "test"
        b.b.get() == 12

        when:
        b.b.set(13)
        then:
        b.number("b") == 12
        b.b.get() == 13
        new ObjectMapper().convertValue(b, Map) == ["b" : 13]
        new ObjectMapper().convertValue(a, Map) == ["a" : "test"]
    }
    def "object node test" () {
        given:
        def jsonData = """
          {
             "a" : { "a" : "test" },
             "b" : { "b" : 11 }
          }
        """
        when:
        DynType kneadable = DynRecord.fromJson(jsonData)
        AB ab = kneadable.cast(AB)
        then:
        ab.a instanceof  OptionalField
        ab.b instanceof  OptionalField
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
        DynType kneadable = DynTypeObject.fromJson(jsonData)
        def kneadAB = kneadable.cast(KneadAB)
        then:
        kneadAB.field.isPresent()
        kneadAB.field.get().cast(A).a.get() == "test"
        kneadAB.field.get().cast(B).b.get() == 1

        when:
        def serialization = new ObjectMapper().convertValue(kneadable, Map)
        then:
        serialization == [field : [a : "test", b : 1]]
    }

    static class A {
//       {
//          "a" : "test"
//       }
        OptionalField<String> a;
    }
    static class B extends TypeForm{
//        {
//          "b" : 12
//        }
        MutableOptionalField<Integer> b;
    }
    static class AB {
//      {
//         "a" : { "a" : "test" }
//         "b" : { "b" : 11 }
//      }
        OptionalField<A> a;
        OptionalField<B> b;
    }
    static class KneadAB {
//      {
//         "field" : { "a" : "test", "b" : 1}
//      }
        OptionalField<DynTypeObject> field;
    }
}
