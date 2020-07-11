package de.upb.sede.composition

import de.upb.sede.beta.MutableExecutorRegistration
import de.upb.sede.exec.MutableExecutorContactInfo
import de.upb.sede.exec.MutableExecutorHandle
import de.upb.sede.requests.resolve.beta.IResolvePolicy
import de.upb.sede.requests.resolve.beta.MutableResolveRequest
import de.upb.sede.requests.resolve.beta.ResolveRequest
import spock.lang.Specification

class RRGenTest extends Specification {

    def "FromResource"() {
        def rr1 = RRGen.fromResource("requests/request1.groovy")
        def rr2 = RRGen.fromResource("requests/request2.groovy")

        expect:
        rr1.composition == "a;b;c;"
        rr1 instanceof ResolveRequest
        rr2.resolvePolicy.returnPolicy == IResolvePolicy.FieldSelection.LISTED
        rr2 instanceof MutableResolveRequest
    }

    def "FromClosure"() {
        def rr = RRGen.fromClosure {
            composition = """
            inst1;
            inst2;
            """
            clientExecutorRegistration = MutableExecutorRegistration.create().tap {
                executorHandle = MutableExecutorHandle.create().tap {
                    contactInfo = MutableExecutorContactInfo.create().tap {
                        qualifier = "client-id"
                    }
                }
            }
        }
        expect:
        rr.clientExecutorRegistration.executorHandle.qualifier == "client-id"
    }
}
