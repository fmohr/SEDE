package ai.services.executor.local

import ai.services.ISDLAssembly
import ai.services.SDLReader
import ai.services.channels.StdLocalChannelService
import ai.services.composition.DeployRequest
import ai.services.core.Primitives
import ai.services.executor.ExecutorFactory
import ai.services.gateway.GatewayFactory
import ai.services.interfaces.ExecutorRegistrant
import ai.services.interfaces.IGateway
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

    File serviceInstanceDir = File.createTempDir()

    StdLocalChannelService channel = new StdLocalChannelService(
        serviceInstanceDir.getAbsolutePath())

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

    def "static insts"() {
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

    def "service instance insts"() {
        def exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .threadNumber(24)
            .addServices("SS.Math", "SS.String", "demo.math.Addierer")
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("Executor1")
        def executor1 = exFactory.build()

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
        def graph = resolution.compositionGraph.find {
            it.executorHandle.qualifier == "Executor1"
        }
        when:
        def graphTaskExecution = executor1.deploy("e1", graph)
        executor1.acq().waitUntilFinished("e1")

        def c = graphTaskExecution.getFieldValue("c")
        def addierer = graphTaskExecution.getFieldValue("addierer")

        then:
        c.type == Primitives.Number.name()
        c.dataField == 31.0
        addierer.isServiceInstanceHandle()
        !addierer.isServiceInstance()
        def handle = addierer.serviceHandle
        handle.executorId == "Executor1"
        new File(serviceInstanceDir, "$Addierer.name/$handle.id").exists()
    }

    def "multiple executors"() {
        def exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .threadNumber(10)
            .addServices("demo.math.Addierer")
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("Executor1")
        exFactory.build()

        exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .threadNumber(10)
            .addServices("SS.Math", "SS.String",
                "demo.math.Gerade")
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("Executor2")
        exFactory.build()

        exFactory = new ExecutorFactory()
        exFactory.configBuilder
            .threadNumber(5)
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("ClientExecutor")
        def clientExecutor = exFactory.build()

        def resolution = gateway.resolve(ResolveRequest.builder().with {
            resolvePolicy(ResolvePolicy.builder()
                .isDotGraphRequested(true)
                .build())
            clientExecutorRegistration(clientExecutor.registration())
            composition("""
                a = SS.Math::addPrimitive({10, 3});
                b = SS.Math::multiplyObject({1, 5});
                addierer = demo.math.Addierer::__construct({a});
                c = addierer::addier({b});
                gerade = demo.math.Gerade::__construct({b,c});
                c = addierer::addier({1});
                p1 = gerade::calc({c});
                b1 = gerade::liegtAufGerade({p1});
                p2 = gerade::nullstelle();
                p3 = demo.math.Addierer::summier({p1, p2});
                b2 = gerade::liegtAufGerade({p3});
            """)
            build()
        })
        def graph = resolution.compositionGraph.find {
            it.executorHandle.qualifier == "Executor1"
        }
        FileUtil.writeStringToFile("composition.svg", resolution.getDotSVG())

        when:
        resolution.compositionGraph.each { compGraph ->
            channel.interExecutorCommChannel(compGraph.executorHandle.contactInfo)
                .deployGraph(DeployRequest.builder().with {
                    executionId("e1")
                    it.compGraph(compGraph)
                    build()
                })
        }

        def graphTaskExecution = clientExecutor.acq().waitUntilFinished("e1")

        def b1 = graphTaskExecution.getFieldValue("b1")
        def b2 = graphTaskExecution.getFieldValue("b2")
        def gerade = graphTaskExecution.getFieldValue("gerade")
        def addierer = graphTaskExecution.getFieldValue("addierer")

        then:
        b1.type == Primitives.Bool.name()
        b2.type == Primitives.Bool.name()
        b1.getDataField() as Boolean
        !b2.getDataField() as Boolean

        [addierer, gerade] .each {
            assert it.isServiceInstanceHandle()
            assert !it.isServiceInstance()
            def handle = it.serviceHandle
            assert new File(serviceInstanceDir, "$it.serviceHandle.classpath/$handle.id").exists()
        }
    }
}
