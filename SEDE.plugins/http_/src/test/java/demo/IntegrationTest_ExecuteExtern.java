package demo;

import de.upb.sede.client.CoreClient;
import de.upb.sede.client.HttpCoreClient;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.ObjectDataField;
import de.upb.sede.core.PrimitiveDataField;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.Execution;
import de.upb.sede.exec.Executor;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.gateway.GatewayHttpServer;
import de.upb.sede.requests.Result;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.util.ExecutorConfigurationCreator;
import de.upb.sede.util.FileUtil;
import demo.math.Addierer;
import demo.types.DemoCaster;
import demo.types.NummerList;
import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@SuppressWarnings("unchecked")
public class IntegrationTest_ExecuteExtern {

	private static final Logger logger = LoggerFactory.getLogger(IntegrationTest_ExecuteExtern.class);

	/*
	 * SET THE ADDRESSES BELOW BEFORE RUNNING TESTS
	 */
	public static final String GATEWAY_HOST_ADDRESS = "localhost";
	public static final int GATEWAY_PORT = 7000;

	public static final String CLIENT_ADDRESS = "localhost";
	public static final int CLIENT_PORT = 17000;

	static CoreClient httpClient;

	@BeforeClass
	public static void setup() {
		/* supports nothing */
		String clientConfig = ExecutorConfigurationCreator.newConfigFile().withExecutorId("Core_Client")
				.toString();
		ExecutorConfiguration config = ExecutorConfiguration.parseJSON(clientConfig);
		httpClient =  HttpCoreClient.createNew(config, CLIENT_ADDRESS, CLIENT_PORT, GATEWAY_HOST_ADDRESS, GATEWAY_PORT);
		httpClient.writeDotGraphToDir("testrsc/graphs/http-exec");
	}

	@AfterClass
	public static void shutdown() {
		httpClient.getClientExecutor().shutdown();
	}

	@Test
	public void testRun() {
		HashMap<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("a", new PrimitiveDataField(10));
		RunRequest runRequest = new RunRequest("rId", "composition blabla", new ResolvePolicy(), inputs);
		System.out.println(runRequest.toJsonString());
	}

	private static CoreClient getHttpClient() {
		return httpClient;
	}
}
