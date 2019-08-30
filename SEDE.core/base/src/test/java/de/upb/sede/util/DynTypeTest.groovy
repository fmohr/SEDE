package de.upb.sede.util

import org.spockframework.compiler.model.ThenBlock
import spock.lang.Specification

class DynTypeTest extends Specification {

    def "test dynamic type"() {
        when:
        def obj = isJson ? DynTypeObject.fromJson(data) : DynTypeObject.fromYaml(data)
        then:
        type_.isInstance(obj.getData())
        obj.data == actualData

        when:
        def casted = obj.cast(type_)
        then:
        casted == actualData

        where:
        _ | isJson | type_
        _ | true   | String
        _ | false  | String
        _ | false  | Number
        _ | false  | Number
        _ | true   | Boolean
        _ | true   | List
        _ | false  | List
        _ | true   | Map
        _ | false  | Map

        data << [
            """
            "json string value 123"
            """,
            """
            yaml string 456
            """,
            """
            101
            """,
            """
            102
            """,
            """
            true
            """,
            """
            ["StringVal", 12, false]
            """,
            """
            -
               StringVal
            -
               12
            -
               false
            """,
            """
            {
                "field a" : 12,
                "field b" : true,
                "field c" : ["a", "b", "c"]
            }
            """,
            """
            "field a": 12
            "field b": true
            "field c":
             -
               a
             -
               b
             -
               c
            """
        ]
        actualData << [
            "json string value 123",
            "yaml string 456",
            101,
            102,
            true,
            ["StringVal", 12, false],
            ["StringVal", 12, false],
            ["field a" : 12, "field b": true, "field c": ["a", "b", "c"]],
            ["field a" : 12, "field b": true, "field c": ["a", "b", "c"]]
        ]
    }

    def "test exceptions"() {
        when:
        def data = """
            null
        """
        DynTypeObject.fromJson(data)
        then:
        thrown(NotKneadableException)

        when:
        data = null
        DynTypeObject.fromJson(data)
        then:
        thrown(NotKneadableException)

    }
}
