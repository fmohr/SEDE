package ai.services.composition


import ai.services.requests.resolve.beta.IResolvePolicy
import spock.lang.Specification

class RRGenTest extends Specification {

    def "FromResource"() {
        def rr1 = RRGen.fromResource("requests/request1.groovy")
        def rr2 = RRGen.fromResource("requests/request2.groovy")

        expect:
        rr1.composition == "a;b;c;"
        rr1 instanceof ai.services.requests.resolve.beta.ResolveRequest
        rr2.resolvePolicy.returnPolicy == IResolvePolicy.FieldSelection.LISTED
        rr2 instanceof ai.services.requests.resolve.beta.MutableResolveRequest
    }

    def "FromClosure"() {
        def rr = RRGen.fromClosure {
            composition = """
            inst1;
            inst2;
            """
            clientExecutorRegistration = ai.services.beta.MutableExecutorRegistration.create().tap {
                executorHandle = ai.services.exec.MutableExecutorHandle.create().tap {
                    contactInfo = ai.services.exec.MutableExecutorContactInfo.create().tap {
                        qualifier = "client-id"
                    }
                }
            }
        }
        expect:
        rr.clientExecutorRegistration.executorHandle.qualifier == "client-id"
    }
}
