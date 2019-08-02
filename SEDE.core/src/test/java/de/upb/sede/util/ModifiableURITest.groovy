package de.upb.sede.util

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class ModifiableURITest extends Specification {

    def "test basic use case"() {
        def uri = new ModifiableURI()
        if(scheme) uri.scheme(scheme)
        if(user) uri.userInfo(user)
        if(host) uri.host(host)
        if(port) uri.path(port)
        if(path) uri.path(path)

        expect:
        uri.buildString() == uriString

        where:
        _ | scheme | user | host | port | path
        _ | "http" | null | "example.com" | null | null
        _ | null | null | "example.com" | null | null

        uriString << [
            "http://example.com",
            "//example.com"
        ]
    }

    def "test parse"() {
        def uriStr = "192.157.32.125#someFragment"
        def uri = ModifiableURI.fromUriString(uriStr)

        expect:
        uri.getPath().get() == "192.157.32.125"
        uri.toString() == "192.157.32.125#someFragment"

        when:
        uri.host(uri.getPath().get())
        uri.replacePath(null)
        uri.scheme("http")
        then:
        uri.getHost().get() == "192.157.32.125"
        uri.getFragment().get() == "someFragment"
        uri.toString() == "http://192.157.32.125#someFragment"
    }

    def "test serialisation"() {
        def uriStr = "http://example.com/path/to/something?k1=v1&k2=v2#fragment"
        def jsonStr = "\"" + uriStr + "\""

        def mapper = new ObjectMapper()
        def uri = mapper.readValue(jsonStr, UnmodifiableURI)

        expect:
        uri.scheme.get() == "http"
        uri.host.get() == "example.com"
        uri.path.get() == "/path/to/something"
        !uri.port.isPresent()
        !uri.userInfo.isPresent()
        uri.queryParams.containsKey("k1")
        uri.queryParams.containsEntry("k2", "v2")
        !uri.queryParams.containsEntry("k2", "v1")
        uri.fragment.get() == "fragment"

        when:
        def builtStr = uri.buildString();
        def expectedStr = uri.buildURI().toString()
        then:
        builtStr == expectedStr
//        builtStr == uriStr

        when:
        def map = [a: uriStr]
        def seriObj = mapper.convertValue(map, SeriTest)
        then:
        seriObj.a == builtStr
    }

    static class SeriTest {
        String a
    }

    def "test fragment mod"() {
        when:
        def uriStr = "10.0.0.8"
        def uri = ModifiableURI.fromUriString(uriStr)
        uri.amendHttpScheme().interpretPathAsHost().fragment("ABC")

        then:
        uri.toString() == "http://10.0.0.8#ABC"
    }

    def "test ascribed services"() {
        when:
        def uriStr = "http://localhost:7000#Weka.ml"
        def uri = ModifiableURI.fromUriString(uriStr)
        uri.amendHttpScheme().interpretPathAsHost()

        then:
        uri.toString() == "http://localhost:7000#Weka.ml"
        uri.scheme.get() == "http"
        uri.host.get() == "localhost"
        uri.port.get() == 7000
        uri.getFragment().get() == "Weka.ml"
    }
}
