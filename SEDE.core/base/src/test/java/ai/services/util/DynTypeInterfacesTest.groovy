package ai.services.util

import com.fasterxml.jackson.databind.ObjectMapper
import ai.services.KneadableInterfaces
import spock.lang.Specification


class DynTypeInterfacesTest extends Specification {


    def "simple fields test" () {
        given:
        def jsonData = """
        {
            "a" : "this is some data",
            "b" : 1
        }
        """
        when:
        def mapper = new ObjectMapper()
        DynType kneadable = mapper.readValue(jsonData, DynRecord)
        KneadableInterfaces.AB ab = kneadable.cast(KneadableInterfaces.AB)
        then:
        ab.getA() == "this is some data"
        ab.getB() == 1f
    }

    def "container test" () {
        given:
        def jsonData = """
        {
            "c" : [
                {
                    "a" : "this is some data",
                    "b" : 1
                },
                {
                    "a" : "this is some other data",
                    "b" : 2.1
                }
            ]
        }
        """
        when:
        def mapper = new ObjectMapper()
        DynType kneadable = mapper.readValue(jsonData, DynRecord)
        KneadableInterfaces.Container container = kneadable.cast(KneadableInterfaces.Container)
        then:
        container.c[0].getA() == "this is some data"
        container.c[0].getB() == 1f
        container.c[1].getA() == "this is some other data"
        container.c[1].getB() == 2.1f
    }

}
