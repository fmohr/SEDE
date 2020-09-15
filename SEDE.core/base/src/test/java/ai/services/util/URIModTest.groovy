package ai.services.util

import spock.lang.Specification

class URIModTest extends Specification {

    def "test path addition" () {
        when:
        def modifiedUri = URIMod.addPath(uri, subpath)
        then:
        modifiedUri == expectedResult
        where:
        uri | subpath | expectedResult
        "0.0.0.1" | "path" | "0.0.0.1/path"
        "0.0.0.1:8080" | "path" | "0.0.0.1:8080/path"
        "http://some:8080/address" | "newpath" | "http://some:8080/address/newpath"
        "http://some:8080/address" | "multiple/subpath" | "http://some:8080/address/multiple/subpath"
        "http://some:8080/address" | "newpath/" | "http://some:8080/address/newpath/"
        "http://some:8080/address" | "multiple/subpath/" | "http://some:8080/address/multiple/subpath/"
        "http://some/address/" | "newpath" | "http://some/address/newpath"
        "http://some/address?key=val&key2=val2" | "newpath" | "http://some/address/newpath?key=val&key2=val2"
        "http://some/address/?key=val&key2=val2" | "newpath" | "http://some/address/newpath?key=val&key2=val2"
        "http://some/address/#fragment" | "newpath" | "http://some/address/newpath#fragment"
        "http://some/address/#fragment" | "newpath/" | "http://some/address/newpath/#fragment"
        "http://some/address#fragment" | "newpath" | "http://some/address/newpath#fragment"
        "http://some/address#fragment" | "newpath/" | "http://some/address/newpath/#fragment"
        "http://some/address?key=val&key2=val2#fragment" | "newpath" | "http://some/address/newpath?key=val&key2=val2#fragment"
        "http://some/address/?key=val&key2=val2#fragment" | "newpath" | "http://some/address/newpath?key=val&key2=val2#fragment"
        "http://some/address/?key=val&key2=val2#fragment" | "multiple/paths/" | "http://some/address/multiple/paths/?key=val&key2=val2#fragment"
    }

    def "test scheme change" () {
        when:
        def modifiedUri = URIMod.setScheme(uri, scheme)
        then:
        modifiedUri == expectedResult
        where:
        uri | scheme | expectedResult
        "192.0.0.1" | "random:" | "random:192.0.0.1"
        "random:192.0.0.1" | "random:" | "random:192.0.0.1"
        "" | "sc" | "sc"
        "example.org" | "http://" | "http://example.org"
        "http://example.org" | "http://" | "http://example.org"
    }
}
