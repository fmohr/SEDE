package demo

import com.fasterxml.jackson.databind.ObjectMapper
import de.upb.sede.client.CoreClient
import de.upb.sede.client.HttpCoreClient
import de.upb.sede.config.ExecutorConfiguration
import de.upb.sede.core.ObjectDataField
import de.upb.sede.core.PrimitiveDataField
import de.upb.sede.core.SEDEObject
import de.upb.sede.requests.ExecutorRegistration
import de.upb.sede.requests.RunRequest
import de.upb.sede.requests.resolve.InputFields
import de.upb.sede.requests.resolve.ResolvePolicy
import de.upb.sede.requests.resolve.ResolveRequest
import de.upb.sede.util.ExecutorConfigurationCreator
import demo.types.DemoCaster
import demo.types.NummerList
import demo.types.Punkt
import org.junit.Assert
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Specification

class ExternHttpExecution extends Specification {

    private static final Logger logger = LoggerFactory.getLogger(IntegrationTest_ExecuteExtern)

    /*
     * SET THE ADDRESSES BELOW BEFORE RUNNING TESTS
     */
    static final String GATEWAY_HOST_ADDRESS = "localhost"
    static final int GATEWAY_PORT = 7000

    static final String CLIENT_ADDRESS = "131.234.76.106"
    static final int CLIENT_PORT = 17000

    static CoreClient httpClient;

    void setup() {
        String clientConfig = ExecutorConfigurationCreator.newConfigFile().withExecutorId("Core_Client")
            .toString();
        ExecutorConfiguration config = ExecutorConfiguration.parseJSON(clientConfig);
        httpClient =  HttpCoreClient.createNew(config, CLIENT_ADDRESS, CLIENT_PORT, GATEWAY_HOST_ADDRESS, GATEWAY_PORT);
        httpClient.writeDotGraphToDir("testrsc/graphs/http-exec");
    }


    void cleanup() {
        try {
            httpClient.clientExecutor.shutdown()
        } catch(Exception ignored) {}
    }

    def "test execution basic"() {
        when:
        String fmComp = """
            gerade1 = demo.math.Gerade::__construct({a,-1});
            p =  gerade1::calc({1});
        """

        def inputs = [:]
        inputs["a"] = new PrimitiveDataField(1)

        def resolvePolicy = new ResolvePolicy()


        RunRequest rr = new RunRequest(fmComp, resolvePolicy, inputs);
        String composition = rr.getComposition();
        ResolvePolicy policy = rr.getPolicy();

        ExecutorRegistration registration = httpClient.getClientExecutor().registration();

        InputFields inputFields = InputFields.fromMap(inputs);

        ResolveRequest resolveRequest = new ResolveRequest("RESOLVE_ID", composition, policy, inputFields, registration);

//        println(new ObjectMapper().writeValueAsString(resolveRequest.toJson()))
//
//        return

        def results = httpClient.blockingRun(rr)
        Punkt p = results["p"].castResultData("demo.types.Punkt", DemoCaster).dataField

        then:
        p.x == 1
        p.y == 0

    }



}
