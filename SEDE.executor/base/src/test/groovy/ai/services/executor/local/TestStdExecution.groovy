package ai.services.executor.local

import ai.services.ISDLAssembly
import ai.services.SDLReader
import ai.services.composition.choerography.emulation.executors.BaseExecutor
import ai.services.composition.graphs.nodes.IInstructionNode
import ai.services.composition.graphs.nodes.InstructionNode
import ai.services.core.PrimitiveDataField
import ai.services.core.PrimitiveType
import ai.services.executor.ExecutorFactory
import ai.services.gateway.GatewayFactory
import ai.services.requests.resolve.beta.ResolvePolicy
import ai.services.requests.resolve.beta.ResolveRequest
import spock.lang.Shared
import spock.lang.Specification

class TestStdExecution extends Specification {


    @Shared ISDLAssembly assembly = null

    void setupSpec() {
        SDLReader reader = new SDLReader();
        try {
            reader.readResource("descs/DemoTestServices.servicedesc.groovy");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read test assembly.", e);
        }
        assembly = reader.getSDLAssembly()
    }

    def "singleExecutor"() {
        def gwFactory = new GatewayFactory()
        gwFactory.addServiceAssembly(assembly)
        gwFactory.useInProcessRegistrant()
        def gateway = gwFactory.build()
        def registrant = gwFactory.buildRegistrant()

        def exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .addServices("SS.Math", "SS.String")
            .executorId("Executor1")

        def executor1 = exFactory.build()
        def channelService = exFactory.buildChannel()

        def resolution = gateway.resolve(ResolveRequest.builder().with {
            resolvePolicy(ResolvePolicy.builder().build())
            clientExecutorRegistration(executor1.registration())
            composition("""
                a = SS.Math::addPrimitive({10, 3});
                b = SS.Math::multiplyObject({a,a});
                c = SS.Math::dividePrimitive({a, b});
            """)
            build()
        })

        def graph = resolution.compositionGraph.find {
            it.executorHandle.qualifier == "Executor1"
        }

        when:
        channelService.interExecutorCommChannel(executor1.contactInfo())
            .deployGraph("e1", graph)

        executor1.acq().waitUntilFinished("e1")

        def a = executor1.acq().get("e1").get().getFieldValue("a")
        def b = executor1.acq().get("e1").get().getFieldValue("b")
        def c = executor1.acq().get("e1").get().getFieldValue("c")


        then:
        [a, b, c].each {
            assert it.isPrimitive()
            assert it.type == PrimitiveType.Number.name()
        }
        a.dataField == 13
        b.dataField == 13 * 13
        c.dataField == 13/(13*13)
    }
}
