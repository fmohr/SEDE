package ai.services.util

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

import java.util.stream.Collectors

class DynTypeObjectTest extends Specification {

    def "test simple knead"(){
        given:
        def jsonData = """
        {
        "a" : 1,
        "b" : 3
        }
        """
        when:
        DynType object = DynRecord.fromJson(jsonData)
        ABean aBean = object.cast(ABean)
        AClone aClone = object.cast(AClone)
        AView aView = object.cast(AView)

        then:
        aBean.a == 1
        aClone.a == 1
        aView.getA() == 1

        when:
        aBean.setA(2)
        then:
        aBean.a == 2
        object.cast(ABean).a == 2
        aClone.a == 1
        aView.getA() == 2

        when:
        aView.setA(3)
        then:
        aBean.a == 2
        object.cast(ABean).a == 3
        aClone.a == 1
        aView.getA() == 3
    }

    def "test unknown properties"() {
        given:
        def jsonData = """
        {
        "c" : true,
        "b" : 3
        }
        """
        when:
        DynType object = DynRecord.fromJson(jsonData)
        ABean bean = object.cast(ABean)

        then:
//        thrown(NotKneadableException)
        bean.a == 0

        when:
        def clone = object.cast(AClone)
        then:
//        thrown(NotKneadableException)
        clone.a == 0

        when:
        AView view = object.cast(AView)
        int a = view.getA()
        then:
        thrown(IllegalArgumentException)

    }

    def "test container knead" () {
        given:
        def jsonData = """
        {
            "bean" : {"a" : 1},
            "view" : {"a" : 10},
            "clone" : {"a" : 100}
        }
        """
        when:
        def kneadable = DynRecord.fromJson(jsonData)
        AContainer container = kneadable.cast(AContainer)

        then:
        container.bean.a == 1
        container.clone.a == 100

        when:
        def a = container.view.getA()
        then:
        a == 10

        when:
        container.bean.setA(2)
        container.clone.setA(200)

        then:
        kneadable.dynObject("bean").knibbleNumber("a") == 1
        kneadable.dynObject("view").knibbleNumber("a") == 10
        kneadable.dynObject("clone").knibbleNumber("a") == 100

        when:
        def bean = container.bean.cast(ABean)
        def view = container.bean.cast(AView)
        def clone = container.bean.cast(AClone)
        then:
        bean.a == 2
        view.a == 2
        clone.a == 2

        when:
        bean = container.view.cast(ABean)
        view = container.view.cast(AView)
        clone = container.view.cast(AClone)
        then:
        bean.a == 10
        view.a == 10
        clone.a == 10

//        when:
//        bean.a = container.clone.knead(ABean)
//        view.a = container.clone.knead(AView)
//        clone.a = container.clone.knead(AClone)
//        then:
//        bean.a == 200
//        view.a == 200
//        clone.a == 200
    }

    def "test array knead"(){
        given:
        def jsonData = """
        {
            "list" :
                [
                    { "a" : 1, "b" : 3 },
                    { "a" : 2, "b" : 4 },
                    { "a" : 3, "b" : 5 },
                    { "a" : 4, "b" : 6 }
                ]
        }
        """

        when:
        DynType object = DynRecord.fromJson(jsonData)
        List<ABean> aList = object.list("list")
                .stream().map {
            ABean bean = it.cast(ABean)
            return bean
        }.collect(Collectors.toList())

        then:
        aList[0].a == 1
        aList[1].a == 2
        aList[2].a == 3
        aList[3].a == 4

        when:
        aList[2].setA(0)
        then:
        object.list("list")[2].cast(ABean).a == 0

    }

    def "test default fields"() {
        when:
        DynType kneadable = DynRecord.fromJson(jsonData)
        def object = kneadable.cast(DefaultFields)

        then:
        object.a == a
        object.b == b
        object.c == c

        where:
        a | b |        c
        1 | 3 |   "default-c-val"
        7 | 0 |   "default-c-val"
        0 | 0 |   "new-value"
        0 | 0 |    null
        jsonData << [
            """
                {
                    "a" : 1,
                    "b" : 3
                }
            """,
            """
                {
                    "a" : 7
                }
            """,
            """
                {
                    "c" : "new-value"
                }
            """,
            """
                {
                    "a" : null,
                    "b" : null,
                    "c" : null
                }
            """
        ]

    }

    def "test jackson serialization" () {
        when:
        def kneadable = new ObjectMapper().readValue(jsonData, DynRecord)
        then:
        kneadable.data["a"] == fieldValue
        fieldClass.isInstance kneadable.data["a"]
        where:
        _ | fieldValue  | fieldClass
        _ | "string"    | String
        _ | 123         | Number
        _ | true        | Boolean
        _ | [1,"abc",true] | List
        _ | [b: "c"]    | Map
        _ | [[1,2], 3]       | List
        _ | [b: [c: "d"]]  | Map
        jsonData << [
            """
                {
                    "a" : "string"
                }
            """,
            """
                {
                    "a" : 123
                }
            """,
            """
                {
                    "a" : true
                }
            """,
            """
                {
                    "a" : [1, "abc", true]
                }
            """,
            """
                {
                    "a" : { "b" : "c" }
                }
            """,
            """
                {
                    "a" : [[1,2], 3]
                }
            """,
            """
                {
                    "a" : { "b" : { "c" : "d" } }
                }
            """
        ]
    }

    static class AContainer {
        ABean bean
        AClone clone
        AView view
    }
//    @JsonDeserialize(using = FormDeserializer.class)
//    @JsonSerialize(using = FormSerializer.class)
    static class ABean extends TypeForm {
        int a

        def setA(int a) {
            Object.setField("a", a)
            this.a = a
        }
    }

    static class AClone {
        int a
    }

    static class AView extends TypeForm  {

        static final String a_FIELD = "a"

        def getA() {
            return Object.number(a_FIELD).intValue()
        }

        def setA(int a) {
            return Object.setField(a_FIELD, a)
        }

    }

    static class DefaultFields {
        int a, b
        String c = "default-c-val"
    }
}
