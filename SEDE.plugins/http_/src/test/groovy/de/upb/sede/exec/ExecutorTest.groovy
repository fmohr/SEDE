package de.upb.sede.exec

import de.upb.sede.client.CoreClient
import de.upb.sede.client.HttpCoreClient
import de.upb.sede.config.ExecutorConfiguration
import de.upb.sede.core.ObjectDataField
import de.upb.sede.core.SEDEObject
import de.upb.sede.core.ServiceInstanceField
import de.upb.sede.core.ServiceInstanceHandle
import de.upb.sede.gateway.ExecutorHandle
import de.upb.sede.gateway.GatewayHttpServer
import de.upb.sede.requests.Result
import de.upb.sede.requests.RunRequest
import de.upb.sede.requests.resolve.ResolvePolicy
import de.upb.sede.util.ExecutorConfigurationCreator
import de.upb.sede.util.WebUtil
import demo.IntegrationTest_Execute
import demo.types.DemoCaster
import demo.types.NummerList
import demo.types.Punkt
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.spockframework.compiler.model.ThenBlock
import spock.lang.Specification

import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicBoolean

class ExecutorTest extends Specification {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationTest_Execute.class);

    static File serviceInstanceDir

    static CoreClient client;

    static GatewayHttpServer gateway;

    void setupSpec() {
        serviceInstanceDir = File.createTempDir()

        gateway = new GatewayHttpServer(9100, IntegrationTest_Execute.getTestClassConfig(), IntegrationTest_Execute.getTestTypeConfig());



        /* supports nothing */
        ExecutorConfiguration clientConfig = ExecutorConfiguration.parseJSON(
            new ExecutorConfigurationCreator().with {
                withServiceStoreLocation(serviceInstanceDir.absolutePath)
                withExecutorId("C1")
                withThreadNumberId(1)
                return it.toString()
            })

        client =  HttpCoreClient.createNew(clientConfig, "localhost", 9003, "localhost", 9100);
    }


    void cleanupSpec() {
        gateway.shutdown();
        client.getClientExecutor().shutdown();
    }

    void setup() {
        gateway.basis.heartbeat()
    }

    def "test deallocataion"() {
        setup:
        ExecutorConfiguration config1 = ExecutorConfiguration.parseJSON(new ExecutorConfigurationCreator().with {
            withServiceStoreLocation(serviceInstanceDir.absolutePath)
            withSupportedServices("demo.math.Addierer")
            withExecutorId("E1")
            withThreadNumberId(1)
            return it.toString()
        })

        ExecutorConfiguration config2 = ExecutorConfiguration.parseJSON(new ExecutorConfigurationCreator().with {
            withServiceStoreLocation(serviceInstanceDir.absolutePath)
            withExecutorId("E2")
            withSupportedServices("demo.math.Gerade")
            withThreadNumberId(1)
            return it.toString()
        })

        def executor1 = new ExecutorHttpServer(config1, "localhost", 9010);
        def executor2 = new ExecutorHttpServer(config2, "localhost", 9002);

        executor1.registerToGateway("localhost:9100");
        executor2.registerToGateway("localhost:9100");


        def runRequest = new RunRequest(
            """
                g1 = demo.math.Gerade::__construct({1, 1});
                g2 = demo.math.Gerade::__construct({-1, -1});
            """, new ResolvePolicy().with {
                persistentServices = ["g1", "g2"]
                return it
            }, [:])
        when:
        def results = client.blockingRun(runRequest)
        Result g1Result = results["g1"]
        Result g2Result = results["g2"]
        ServiceInstanceHandle g1Handle = ((ServiceInstanceField) g1Result.resultData).dataField
        ServiceInstanceHandle g2Handle = ((ServiceInstanceField) g2Result.resultData).dataField
        then:
        client.assertErrorFreeRun(results)
        g1Handle != null
        g2Handle != null
        g1Handle.executorId == "E2"
        g2Handle.executorId == "E2"
        !g1Handle.serviceInstance.isPresent()

        when:
        File serviceFiles = new File(serviceInstanceDir, "demo.math.Gerade")
        File g1File = new File(serviceFiles, g1Handle.id)
        File g2File = new File(serviceFiles, g2Handle.id)

        then:
        g1File.isFile()
        g2File.isFile()
        g1File.size() > 5
        g2File.size() > 5


        when:
        client.deallocateAll([g1Handle], "localhost:9100")
        then:
        !g1File.exists()

        when:
        boolean localDeallocResult = executor2.basisExecutor.deallocate(g2Handle)
        then:
        localDeallocResult
        !g2File.exists()

        cleanup:
        executor1.shutdown();
        executor2.shutdown();
    }

    def "test execution in the same group"() {
        when:
        ExecutorConfiguration config3 = ExecutorConfiguration.parseJSON(new ExecutorConfigurationCreator().with {
            withServiceStoreLocation(serviceInstanceDir.absolutePath)
            withSupportedServices("demo.math.Gerade")
            withExecutorId("E3")
            withGroupId("G1")
            withThreadNumberId(1)
            return it.toString()
        })
        def executor3 = new ExecutorHttpServer(config3, "localhost", WebUtil.nextFreePort(9010));
        executor3.registerToGateway("localhost:9100");

        def runRequest = new RunRequest(
            """
                g1 = demo.math.Gerade::__construct({1, 1});
            """,
            new ResolvePolicy().with {
                persistentServices = ["g1"]
                return it
            },
            [:])
        def results = client.blockingRun(runRequest)
        Result g1Result = results["g1"]
        ServiceInstanceHandle g1Handle = ((ServiceInstanceField) g1Result.resultData).dataField

        then:
        client.assertErrorFreeRun(results)
        g1Handle != null
        g1Handle.executorId == "G1"

        when:
        executor3.shutdown()
        gateway.basis.heartbeat()

        then:
        !gateway.basis.executorCoord.hasExecutor("E3")

        when:
        ExecutorConfiguration config4 = ExecutorConfiguration.parseJSON(new ExecutorConfigurationCreator().with {
            withServiceStoreLocation(serviceInstanceDir.absolutePath)
            withSupportedServices("demo.math.Gerade")
            withExecutorId("E4")
            withGroupId("G1")
            withThreadNumberId(1)
            return it.toString()
        })
        def executor4 = new ExecutorHttpServer(config4, "localhost",  WebUtil.nextFreePort(9010));
        executor4.registerToGateway("localhost:9100");

        runRequest = new RunRequest(
            """
                p =  g1::calc({1});
            """,
            new ResolvePolicy().with {
                returnFieldnames = ["p"]
                return it
            },
            [g1:new ServiceInstanceField(g1Handle)])

        results = client.blockingRun(runRequest)
        ObjectDataField point = results["p"].castResultData(Punkt.name, DemoCaster)

        then:
        point != null
        point.dataField instanceof Punkt
        (point.dataField as Punkt).x == 1
        (point.dataField as Punkt).y == 2


        when:
        client.deallocateAll([g1Handle], "localhost:9100")
        File serviceFiles = new File(serviceInstanceDir, "demo.math.Gerade")
        File g1File = new File(serviceFiles, g1Handle.id)

        then:
        !g1File.exists()


        cleanup:
        executor3.shutdown()
        executor4.shutdown()
    }

    def "test sleep instruction interrupt"() {
        setup:
        ExecutorConfiguration config1 = ExecutorConfiguration.parseJSON(new ExecutorConfigurationCreator().with {
            withServiceStoreLocation(serviceInstanceDir.absolutePath)
            withSupportedServices("demo.math.Addierer")
            withExecutorId("E5")
            withThreadNumberId(2)
            return it.toString()
        })

        ExecutorConfiguration config2 = ExecutorConfiguration.parseJSON(new ExecutorConfigurationCreator().with {
            withServiceStoreLocation(serviceInstanceDir.absolutePath)
            withExecutorId("E6")
            withSupportedServices("demo.math.Gerade")
            withThreadNumberId(2)
            return it.toString()
        })

        def executor5 = new ExecutorHttpServer(config1, "localhost", 9011);
        def executor6 = new ExecutorHttpServer(config2, "localhost", 9012);
        def gw = gateway
        executor5.registerToGateway("localhost:9100");
        executor6.registerToGateway("localhost:9100");


        def runRequest = new RunRequest(
            """
                g1 = demo.math.Gerade::__construct({1, 1});
                a=demo.math.Addierer::__construct({100});
                d=a::addier({20});
                f=a::addierListe({b});
                d=g1::liagtAufGerade({f});
                demo.math.Addierer::sleep();
            """, new ResolvePolicy().with {
            returnFieldnames = ["d", "f"]
            return it
        }, [b: new ObjectDataField("demo.types.NummerList", new NummerList([1,2]))])
        when:
        def receivedResults = []
        def client = this.client
        def execId = client.run(runRequest, {
            if(it.hasFailed())
                throw new RuntimeException("Result ${it.toJsonString()} failed")
            receivedResults += it.fieldname
        })
        Thread.sleep(300)
        def executions = [
            client.clientExecutor.execPool.getExecution(execId).get(),
            executor5.getBasisExecutor().execPool.getExecution(execId).get(),
            ]

        then:
        executions.every { it.hasStarted() }
        executions.every { !it.hasExecutionFinished() }
        executions.every { !it.hasExecutionBeenInterrupted() }

        /*
         * Join the execution; interrupt after 100 ms without propagating interruption
         */
        when:
        def propagateInterruption = new AtomicBoolean(false)
        def joinRunnable = {
            client.join(execId, propagateInterruption.get())
        } as Runnable
        def joiner = new Thread(joinRunnable, "Join1")
        joiner.start()
        then:
        joiner.isAlive()
        when:
        Thread.sleep(100)
        then:
        joiner.isAlive()
        when:
        joiner.interrupt()
        Thread.sleep(100)
        then:
        !joiner.isAlive()
        !joiner.isInterrupted()
        executions.every { it.hasStarted() }
        executions.every { !it.hasExecutionFinished() }
        executions.every { !it.hasExecutionBeenInterrupted() }

        /*
         * Join the execution, interrupt after 1000 ms and propagate interruption
         */
        when:
        propagateInterruption.set(true)
        def joiner2 = new Thread(joinRunnable, "Join2")
        joiner2.start()
        Thread.sleep(100)
        then:
        joiner2.isAlive()
        when:
        joiner2.interrupt()
        Thread.sleep(100)
        then:
        !joiner2.isAlive()
        !joiner2.isInterrupted()
        executions.every { it.hasStarted() }
        executions.every { it.hasExecutionFinished() }
        executions.every { it.hasExecutionBeenInterrupted() }
        receivedResults.contains("d")
        receivedResults.contains("f")


        cleanup:
        executor5.shutdown()
        executor6.shutdown()


    }

}
