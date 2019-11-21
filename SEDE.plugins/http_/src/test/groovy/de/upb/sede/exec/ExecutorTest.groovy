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
import demo.types.Punkt
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

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

}
