package de.upb.sede.exec

import de.upb.sede.client.CoreClient
import de.upb.sede.client.HttpCoreClient
import de.upb.sede.config.ExecutorConfiguration
import de.upb.sede.core.ServiceInstanceField
import de.upb.sede.core.ServiceInstanceHandle
import de.upb.sede.gateway.ExecutorHandle
import de.upb.sede.gateway.GatewayHttpServer
import de.upb.sede.requests.Result
import de.upb.sede.requests.RunRequest
import de.upb.sede.requests.resolve.ResolvePolicy
import de.upb.sede.util.ExecutorConfigurationCreator
import demo.IntegrationTest_Execute
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

class ExecutorTest extends Specification {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationTest_Execute.class);

    static File serviceInstanceDir

    static ExecutorHttpServer executor1;
    static ExecutorHttpServer executor2;

    static CoreClient client;

    static GatewayHttpServer gateway;

    void setupSpec() {
        serviceInstanceDir = File.createTempDir()

        gateway = new GatewayHttpServer(9100, IntegrationTest_Execute.getTestClassConfig(), IntegrationTest_Execute.getTestTypeConfig());


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


        executor1 = new ExecutorHttpServer(config1, "localhost", 9010);
        executor2 = new ExecutorHttpServer(config2, "localhost", 9002);

        executor1.registerToGateway("localhost:9100");
        executor2.registerToGateway("localhost:9100");

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
        executor1.shutdown();
        executor2.shutdown();
        client.getClientExecutor().shutdown();
    }

    def "test deallocataion"() {
        setup:
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
    }

}
