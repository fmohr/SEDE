package de.upb.sede

import de.upb.sede.exec.MethodDesc
import de.upb.sede.exec.ServiceDesc
import spock.lang.Specification

class IServiceDescTest extends Specification {

    def "test json integration" () {
        given:
        def serviceDesc = ServiceDesc.builder()
            .qualifier("service.A")
            .addMethods(MethodDesc.builder()
                .qualifier("m1")
                .
                .build())
            .build()

        expect:
        serviceDesc.qualifier == "service.A"
        serviceDesc.hashCode() == serviceDesc.hashCode()

    }
}
