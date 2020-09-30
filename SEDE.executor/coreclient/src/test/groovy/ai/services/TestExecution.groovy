package ai.services

import ai.services.channels.StdLocalChannelService
import ai.services.core.Primitives
import ai.services.executor.ExecutorFactory
import ai.services.executor.local.LocalExecutorInstanceRegistry
import ai.services.gateway.GatewayFactory
import ai.services.interfaces.ExecutorRegistrant
import ai.services.interfaces.IGateway
import ai.services.requests.resolve.beta.ResolvePolicy
import ai.services.requests.resolve.beta.ResolveRequest
import demo.math.Addierer
import spock.lang.Shared
import spock.lang.Specification

import static spock.util.matcher.HamcrestMatchers.*
import static spock.util.matcher.HamcrestSupport.expect

class TestExecution extends Specification {

    @Shared ISDLAssembly assembly = null

    IGateway gateway;

    ExecutorRegistrant registrant;

    File serviceInstanceDir = File.createTempDir()

    StdLocalChannelService channel = new StdLocalChannelService(
        serviceInstanceDir.getAbsolutePath())


    CoreClient coreClient;

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
        gateway = gwFactory.build()
        registrant = gwFactory.buildRegistrant()
        coreClient = new CoreClient(gateway, channel)
    }

    void cleanup() {
        def registry = LocalExecutorInstanceRegistry.INSTANCE;
        for(String key : registry.keys()) {
            def executor = registry.get(key)
            executor.shutdown()
        }
        registry.clear()
    }

    def "static insts"() {
        def exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .addServices("SS.Math", "SS.String")
            .executorId("Executor1")
        def executor1 = exFactory.build()

        def rr = ResolveRequest.builder().with {
            resolvePolicy(ResolvePolicy.builder().build())
            clientExecutorRegistration(executor1.getRegistration())
            composition("""
                a = SS.Math::addPrimitive({10, 3});
                b = SS.Math::multiplyObject({a,a});
                c = SS.Math::dividePrimitive({a, b});
            """)
            build()
        }

        def a,b,c;
        when:
        try(ExecutionController ctrl = coreClient.bootAndStart(rr)) {
            ctrl.waitUntilFinished()
            a = ctrl.getReturnValue("a")
            b = ctrl.getReturnValue("b")
            c = ctrl.getReturnValue("c")
        }

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

        def rr = ResolveRequest.builder().with {
            resolvePolicy(ResolvePolicy.builder().build())
            clientExecutorRegistration(executor1.getRegistration())
            composition("""
                a = SS.Math::addPrimitive({10, 3});
                b = SS.Math::multiplyObject({1, 5});
                addierer = demo.math.Addierer::__construct({a});
                c = addierer::addier({b});
                c = addierer::addier({c});
            """)
            build()
        }

        def c, addierer
        when:
        try(ExecutionController ctrl = coreClient.bootAndStart(rr)) {
            ctrl.waitUntilFinished()
            c = ctrl.getReturnValue("c")
            addierer = ctrl.getReturnValue("addierer")
        }

        then:
        c.type == Primitives.Number.name()
        c.dataField == 31.0
        addierer.isServiceInstanceHandle()
        !addierer.isServiceInstance()
        def handle = addierer.serviceHandle
        handle.executorId == "Executor1"
        new File(serviceInstanceDir, "$Addierer.name/$handle.id").exists()
    }

    def "multipleexecutors"() {
        def exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .threadNumber(24)
            .addServices("demo.math.Addierer")
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("Executor1")
        def executor1 = exFactory.build()

        exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .threadNumber(24)
            .addServices("SS.Math", "SS.String",
                "demo.math.Gerade")
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("Executor2")
        def executor2 = exFactory.build()

        exFactory = new ExecutorFactory()
        exFactory.configBuilder
            .threadNumber(24)
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("ClientExecutor")
        def clientExecutor = exFactory.build()

        def rr = ResolveRequest.builder().with {
            resolvePolicy(ResolvePolicy.builder()
                .isDotGraphRequested(true)
                .build())
            clientExecutorRegistration(clientExecutor.getRegistration())
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
        }
        def resolution = gateway.resolve(rr)
//        FileUtil.writeStringToFile("composition.svg", resolution.getDotSVG())

        def b1
        def b2
        def gerade
        def addierer

//        def runs = []
//
//        when:
//        for (i in 0..<2000) {
//            runs.add(coreClient.boot(rr, resolution))
//            def ctrl = runs[-1]
//        }
//        for(ExecutionController ctrl : runs) {
//            ctrl.waitUntilFinished()
//            ctrl.close()
//        }
//
//        then:
//        true

        when:
        try(ExecutionController ctrl = coreClient.bootAndStart(rr, resolution)) {
            ctrl.waitUntilFinished()
            b1 = ctrl.getReturnValue("b1")
            b2 = ctrl.getReturnValue("b2")
            gerade = ctrl.getReturnValue("gerade")
            addierer = ctrl.getReturnValue("addierer")
        }

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

        [clientExecutor, executor1, executor2].each {
            assert !it.acq().get("e1").isPresent()
        }
    }

    def "test single execution fail"() {
        def exFactory = new ExecutorFactory()
        exFactory.registerToLocalGateway(registrant)
        exFactory.configBuilder
            .threadNumber(10)
            .addServices("SS.Math", "demo.math.Addierer")
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("Executor1")
        def executor1 = exFactory.build()

        exFactory = new ExecutorFactory()
        exFactory.configBuilder
            .threadNumber(5)
            .serviceStoreLocation(serviceInstanceDir.absolutePath)
            .executorId("ClientExecutor")
        def clientExecutor = exFactory.build()

        def rr = ResolveRequest.builder().with {
            resolvePolicy(ResolvePolicy.builder()
                .isDotGraphRequested(true)
                .build())
            clientExecutorRegistration(clientExecutor.getRegistration())
            composition("""
                a = SS.Math::addPrimitive({0, 1});
                b = demo.math.Addierer::fail();
                a = SS.Math::addPrimitive({a, 2});
                demo.math.Addierer::useNummerList({b});
            """)
            build()
        }


        when:
        ExecutionController ctrl = coreClient.bootAndStart(rr)
        ctrl.waitUntilFinished()

        then:
        thrown(ExecutionErrorException)

        when:
        ctrl.getReturnValue("e1")
        then:
        thrown(IllegalStateException)

        when:
        ctrl.getReturnValue("a")
        then:
        thrown(IllegalStateException)

        when:
        ctrl.getReturnValue("b")
        then:
        thrown(IllegalStateException)

        cleanup:
        ctrl.close()
     }
}
