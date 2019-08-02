package de.upb.sede.edd.deploy

import com.fasterxml.jackson.databind.ObjectMapper
import de.upb.sede.util.FileUtil
import spock.lang.Specification

class AscribedServiceTest extends Specification {

    def "test parsing url fragments" () {
        def ipMarketService = "0.1.2.3:7000#service"
        when:
        def ms = AscribedService.parseURI(ipMarketService)
        then:
        ms.service == "service"
        ms.getNamespace().getEncodedAddress() == "0.1.2.3%3A7000"
        ms.getNamespace().getAddress() == "0.1.2.3:7000"

        when:
        ipMarketService = "http://localhost:7000#service"
        ms = AscribedService.parseURI(ipMarketService)
        then:
        ms.service == "service"
        ms.namespace.scheme == "http"
        ms.getNamespace().getEncodedAddress() == "localhost%3A7000"
        ms.getNamespace().getAddress() == "localhost:7000"
    }

    def "test parsing file and cp fragments" () {
        def ipMarketService = "classpath://deployment/sede.services-deployconf.json#Weka.ml"
        when:
        def ms = AscribedService.parseURI(ipMarketService)
        then:
        ms.service == "Weka.ml"
        ms.getNamespace().scheme == "classpath"
        ms.getNamespace().getAddress() == "deployment/sede.services-deployconf.json"
        ms.getNamespace().getEncodedAddress() == "deployment%2Fsede.services-deployconf.json"
        FileUtil.readResourceAsString(ms.getNamespace().getAddress()).size() > 0
    }

    def "test json serialization"() {
        def jsonData = """
            "http://some.host.address/subpath#service.framework"
        """
        when:
        ObjectMapper mapper = new ObjectMapper()
        def ascribedService = mapper.readValue(jsonData, AscribedService)
        then:
        ascribedService.namespace.toString() == "http://some.host.address/subpath"
        ascribedService.namespace.scheme == "http"
        ascribedService.namespace.address == "some.host.address/subpath"
        ascribedService.service == "service.framework"

        when:
        def jsonOut = mapper.writeValueAsString(ascribedService)
        then:
        jsonOut == "\"http://some.host.address/subpath#service.framework\""

    }
}
