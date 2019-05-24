package de.upb.sede.util

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import spock.lang.Specification

import java.util.stream.Collectors

class KneadableJsonObjectTest extends Specification {

    def "test simple knead"(){
        given:
        def jsonData = """
        {
        "a" : 1,
        "b" : 3
        }
        """
        when:
        Kneadable object = KneadableJsonObject.fromJson(jsonData)
        ABean aBean = object.knead(ABean)
        AClone aClone = object.knead(AClone)
        AView aView = object.knead(AView)

        then:
        aBean.a == 1
        aClone.a == 1
        aView.getA() == 1

        when:
        aBean.setA(2)
        then:
        aBean.a == 2
        object.knead(ABean).a == 2
        aClone.a == 1
        aView.getA() == 2

        when:
        aView.setA(3)
        then:
        aBean.a == 2
        object.knead(ABean).a == 3
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
        Kneadable object = KneadableJsonObject.fromJson(jsonData)
        ABean bean = object.knead(ABean)

        then:
//        thrown(NotKneadableException)
        bean.a == 0

        when:
        def clone = object.knead(AClone)
        then:
//        thrown(NotKneadableException)
        clone.a == 0

        when:
        AView view = object.knead(AView)
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
        def kneadable = KneadableJsonObject.fromJson(jsonData)
        AContainer container = kneadable.knead(AContainer)

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
        kneadable.knibbleObject("bean").knibbleNumber("a") == 1
        kneadable.knibbleObject("view").knibbleNumber("a") == 10
        kneadable.knibbleObject("clone").knibbleNumber("a") == 100

        // TODO Implement kneadform serialization
//        when:
//        def bean = container.bean.knead(ABean)
//        def view = container.bean.knead(AView)
//        def clone = container.bean.knead(AClone)
//        then:
//        bean.a == 1
//        view.a == 1
//        clone.a == 1

//        when:
//        bean = container.view.knead(ABean)
//        view = container.view.knead(AView)
//        clone = container.view.knead(AClone)
//        then:
//        bea.a == 10
//        view.a == 10
//        clone.a == 10
//
//        when:
//        bean.a = container.clone.knead(ABean)
//        view.a = container.clone.knead(AView)
//        clone.a = container.clone.knead(AClone)
//        then:
//        bea.a == 100
//        view.a == 100
//        clone.a == 100
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
        Kneadable object = KneadableJsonObject.fromJson(jsonData)
        List<ABean> aList = object.knibbleList("list")
                .stream().map {
            ABean bean = it.knead(ABean)
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
        object.knibbleList("list")[2].knead(ABean).a == 0

    }

    static class AContainer {
        ABean bean
        AClone clone
        AView view
    }
//    @JsonDeserialize(using = FormDeserializer.class)
//    @JsonSerialize(using = FormSerializer.class)
    static class ABean extends KneadForm {
        int a

        def setA(int a) {
            super.setField("a", a)
            this.a = a
        }
    }

    static class AClone {
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
