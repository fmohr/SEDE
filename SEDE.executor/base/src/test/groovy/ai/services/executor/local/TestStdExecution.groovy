package ai.services.executor.local

import ai.services.ISDLAssembly
import ai.services.SDLReader
import ai.services.core.Primitives
import ai.services.executor.ExecutorFactory
import ai.services.gateway.GatewayFactory
import ai.services.interfaces.ExecutorRegistrant
import ai.services.interfaces.IGateway
import ai.services.requests.resolve.beta.Choreography
import ai.services.requests.resolve.beta.ResolvePolicy
import ai.services.requests.resolve.beta.ResolveRequest
import ai.services.util.FileUtil
import demo.math.Addierer
import spock.lang.Shared
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.*
import static spock.util.matcher.HamcrestSupport.expect

class TestStdExecution extends Specification {

    @Shared ISDLAssembly assembly = null

    IGateway gateway;

    ExecutorRegistrant registrant;

    void setupSpec() {
        SDLReader reader = new SDLReader();
        try {
            reader.readResource("descs/DemoTestServices.servicedesc.groovy");
            reader.readResource("descs/demolib.servicedesc.groovy")
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read test assembly.", e);
        }
        assembly = reader.getSDLAssembly()
    }

    void setup() {
        def gwFactory = new GatewayFactory()
        gwFactory.addServiceAssembly(assembly)
//        gwFactory.useInProcessRegistrant()
        gateway = gwFactory.build()
        registrant = gwFactory.buildRegistrant()
    }


//    def "test null entries in map"(){
//        when:
//        NopNode.builder().runtimeAuxiliaries(Collections.singletonMap("a", 1)).build()
//        then:
//        thrown(NullPointerException)
//    }

    def "single executor static insts"() {
        def exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .addServices("SS.Math", "SS.String")
            .executorId("Executor1")
        def executor1 = exFactory.build()

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
        def graphTaskExecution = executor1.deploy("e1", graph)
        executor1.acq().waitUntilFinished("e1")

        def a = graphTaskExecution.getFieldValue("a")
        def b = graphTaskExecution.getFieldValue("b")
        def c = graphTaskExecution.getFieldValue("c")


        then:
        [a, b, c].each {
            assert it.isPrimitive()
            assert it.type == Primitives.Number.name()
        }
        a.dataField == 13
        b.dataField == 13 * 13
        expect c.dataField, closeTo(13/(13*13), 0.001)
    }

    def "single executor service instance"() {
        def serviceInstanceDir = File.createTempDir()
        def exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .threadNumber(24)
            .addServices("SS.Math", "SS.String", "demo.math.Addierer")
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("Executor1")
        def executor1 = exFactory.build()
        println("Services are stored in: " + serviceInstanceDir)

        def resolution = gateway.resolve(ResolveRequest.builder().with {
            resolvePolicy(ResolvePolicy.builder().build())
            clientExecutorRegistration(executor1.registration())
            composition("""
                a = SS.Math::addPrimitive({10, 3});
                b = SS.Math::multiplyObject({1, 5});
                addierer = demo.math.Addierer::__construct({a});
                c = addierer::addier({b});
                c = addierer::addier({c});
            """)
            build()
        })
        String dotSVG = resolution.getDotSVG()
        File outF = new File(serviceInstanceDir, "graph.svg");
        FileUtil.writeStringToFile(outF.getAbsolutePath(), dotSVG);
        def graph = resolution.compositionGraph.find {
            it.executorHandle.qualifier == "Executor1"
        }
        when:
        def graphTaskExecution = executor1.deploy("e1", graph)
        executor1.acq().waitUntilFinished("e1")

        def a = graphTaskExecution.getFieldValue("a")
        def b = graphTaskExecution.getFieldValue("b")
        def c = graphTaskExecution.getFieldValue("c")
        def addierer = graphTaskExecution.getFieldValue("addierer")

        then:
        c.type == Primitives.Number.name()
        c.dataField == 31.0
        addierer.isServiceInstanceHandle()
        !addierer.isServiceInstance()
        def handle = addierer.serviceHandle
        handle.executorId == "Executor1"
        new File(serviceInstanceDir, Addierer.name + "/" + handle.id).exists()

    }
}
