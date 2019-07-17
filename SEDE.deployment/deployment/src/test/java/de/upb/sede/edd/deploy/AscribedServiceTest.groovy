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
        ms.getSpecUri().getEncodedAddress() == "0.1.2.3%3A7000"
        ms.getSpecUri().getAddress() == "0.1.2.3:7000"

        when:
        ipMarketService = "http://localhost:7000#service"
        ms = AscribedService.parseURI(ipMarketService)
        then:
        ms.service == "service"
        ms.specUri.scheme == "http"
        ms.getSpecUri().getEncodedAddress() == "localhost%3A7000"
        ms.getSpecUri().getAddress() == "localhost:7000"
    }

    def "test parsing file and cp fragments" () {
        def ipMarketService = "classpath://deployment/sede.services-deployconf.json#Weka.ml"
        when:
        def ms = AscribedService.parseURI(ipMarketService)
        then:
        ms.service == "Weka.ml"
        ms.getSpecUri().scheme == "classpath"
        ms.getSpecUri().getAddress() == "deployment/sede.services-deployconf.json"
        ms.getSpecUri().getEncodedAddress() == "deployment%2Fsede.services-deployconf.json"
        FileUtil.readResourceAsString(ms.getSpecUri().getAddress()).size() > 0
    }

    def "test json serialization"() {
        def jsonData = """
            "http://some.host.address/subpath#service.framework"
        """
        when:
        ObjectMapper mapper = new ObjectMapper()
        def ascribedService = mapper.readValue(jsonData, AscribedService)
        then:
        ascribedService.specUri.toString() == "http://some.host.address/subpath"
        ascribedService.specUri.scheme == "http"
        ascribedService.specUri.address == "some.host.address/subpath"
        ascribedService.service == "service.framework"

        when:
        def jsonOut = mapper.writeValueAsString(ascribedService)
        then:
        jsonOut == "\"http://some.host.address/subpath#service.framework\""

    }
}
