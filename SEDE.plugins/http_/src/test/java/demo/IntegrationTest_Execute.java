package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@SuppressWarnings("unchecked")
public class IntegrationTest_Execute {
	private static final Logger logger = LoggerFactory.getLogger(IntegrationTest_Execute.class);

	private static final String rscPath = FileUtil.getPathOfResource("run-requests/");

	static ExecutorHttpServer executor1;
	static ExecutorHttpServer executor2;

	static CoreClient localClient;
	static CoreClient httpClient;

	static GatewayHttpServer gateway;

	@BeforeClass
	public static void setup() {
		String exec1Config = ExecutorConfigurationCreator.newConfigFile().withExecutorId("Executor_1")
				.withSupportedServices("demo.math.Addierer").toString();
		ExecutorConfiguration config = ExecutorConfiguration.parseJSON(exec1Config);
		executor1 = new ExecutorHttpServer(config, "localhost", 9010);

		String exec2Config = ExecutorConfigurationCreator.newConfigFile().withExecutorId("Executor_2")
				.withSupportedServices("demo.math.Gerade").toString();
		config = ExecutorConfiguration.parseJSON(exec2Config);
		executor2 = new ExecutorHttpServer(config, "localhost", 9002);

		gateway = new GatewayHttpServer(9100, getTestClassConfig(), getTestTypeConfig());

		executor1.registerToGateway("localhost:9100");
		executor2.registerToGateway("localhost:9100");

		String clientConfig = ExecutorConfigurationCreator.newConfigFile().withExecutorId("Core_Client_Services")
				.withSupportedServices("demo.math.Addierer", "demo.math.Gerade").toString();
		config = ExecutorConfiguration.parseJSON(clientConfig);
		Executor clientExecutor = new Executor(config);
		localClient = new CoreClient(clientExecutor, gateway.getBasis()::resolve);
		localClient.writeDotGraphToDir("testrsc/graphs/local-exec");

		/* supports nothing */
		clientConfig = ExecutorConfigurationCreator.newConfigFile().withExecutorId("Core_Client")
				.toString();
		config = ExecutorConfiguration.parseJSON(clientConfig);
		httpClient =  HttpCoreClient.createNew(config, "localhost", 9003, "localhost", 9100);
		httpClient.writeDotGraphToDir("testrsc/graphs/http-exec");

	}

	@AfterClass
	public static void shutdown() {
		gateway.shutdown();
		executor1.shutdown();
		executor2.shutdown();
		httpClient.getClientExecutor().shutdown();
	}

	@Test
	public void testHttpInterrupt() throws InterruptedException {
		CoreClient cc = getHttpClient();
		testInterruptExecution(cc);
		Thread.sleep(1000);
		Execution exec = cc.getClientExecutor().getExecution("SleepRequest").orElse(null);
		if(exec!=null) {
			Assert.assertTrue(exec.hasExecutionFinished());
			Assert.assertFalse(cc.getClientExecutor().getWorkerPool().isExecutionOngoing(exec));
			logger.info("Execution has beed interrupted.");
		}
		exec = executor1.getBasisExecutor().getExecution("SleepRequest").orElse(null);
		if(exec!=null) {
			Assert.assertTrue(exec.hasExecutionFinished());
			Assert.assertFalse(cc.getClientExecutor().getWorkerPool().isExecutionOngoing(exec));
			logger.info("Execution has beed interrupted.");
		}

		exec = executor2.getBasisExecutor().getExecution("SleepRequest").orElse(null);
		if(exec!=null) {
			Assert.assertTrue(exec.hasExecutionFinished());
			Assert.assertFalse(cc.getClientExecutor().getWorkerPool().isExecutionOngoing(exec));
			logger.info("Execution has beed interrupted.");
		}
	}


	@Test
	public void testLocalInterrupt() throws InterruptedException {
		CoreClient cc = getLocalClient();
		testInterruptExecution(cc);
		if(cc.getClientExecutor().getExecPool().hasExecution("SleepRequest")) {
			Execution clientExec = cc.getClientExecutor().getExecution("SleepRequest").orElse(null);
			Thread.sleep(100);
			if (clientExec != null) {
				Assert.assertTrue(clientExec.hasExecutionFinished());
				Assert.assertFalse(cc.getClientExecutor().getWorkerPool().isExecutionOngoing(clientExec));
				logger.info("Execution has beed interrupted.");
			}
		}
	}


	public void testInterruptExecution(CoreClient cc) throws InterruptedException {
		String sleepInstruction = 	"c = demo.math.Addierer::summierListe({a,b});" +
									"demo.math.Addierer::sleep()";

		Map<String, SEDEObject> inputs = new HashMap<>();
		NummerList a = new NummerList(Arrays.asList(1,2,3));
		NummerList b = new NummerList(Arrays.asList(3,2,1));
		SEDEObject inputA = new ObjectDataField(NummerList.class.getName(), a);
		SEDEObject inputB = new ObjectDataField(NummerList.class.getName(), b);
		inputs.put("a", inputA);
		inputs.put("b", inputB);

		RunRequest rr = new RunRequest("SleepRequest", sleepInstruction, new ResolvePolicy(), inputs);
		CoreClient.MapResultConsumer results = new CoreClient.MapResultConsumer();
		String requestId = cc.run(rr, results);
		Assert.assertEquals("SleepRequest", requestId);

		Thread.sleep(300); // wait for the result to be calculated.

		cc.interrupt(requestId);

		Assert.assertTrue(results.containsKey("c"));
		Assert.assertFalse(results.get("c").hasFailed());
		NummerList c = (NummerList) results.get("c").castResultData("demo.types.NummerList", DemoCaster.class).getDataField();

		Assert.assertEquals(Addierer.summierListe(a,b), c);
	}


	@Test
	public void testLocalFail() {
		CoreClient cc = getLocalClient();
		runFailInstruction(cc);
	}

	@Test
	public void testHttpFail() {
		CoreClient cc = getHttpClient();
		runFailInstruction(cc);
	}

	public void runFailInstruction(CoreClient cc) {
		String failInstruction = "a = demo.math.Addierer::fail()";

		RunRequest rr = new RunRequest("FailRequest", failInstruction, new ResolvePolicy(), Collections.EMPTY_MAP);
		Map<String, Result> resultMap = cc.blockingRun(rr);
		Assert.assertTrue(resultMap.containsKey("a"));
		Assert.assertTrue(resultMap.get("a").hasFailed());
		Assert.assertTrue(resultMap.containsKey(cc.getErrorFieldName()));
//		System.out.println((String) resultMap.get(cc.getErrorFieldName()).getResultData().getDataField());
		System.out.println(JSONObject.toJSONString(resultMap.get(cc.getErrorFieldName()).getResultData().getDataField()));
		try{
			cc.assertErrorFreeRun(resultMap);
			Assert.fail("Error free run didnt throw exception.");
		} catch (Exception ex) {
			logger.debug("Corretcly throw error: ", ex);
		}
	}

	@Test
	public void testLocalRun()throws Exception {
		CoreClient cc = getLocalClient();
		runAllOnce(cc);
	}

	@Test
	public void  testHttpRun()throws Exception {
		CoreClient cc = getHttpClient();
		runAllOnce(cc);
		//cc.getClientExecutor().shutdown();
	}

	public void runAllOnce(CoreClient cc) throws Exception {

		for (String pathToRequest : FileUtil.listAllFilesInDir(rscPath, "(.*?)\\.json$")) {
			pathToRequest = rscPath + pathToRequest;
			logger.info("Running execution from: {}", pathToRequest);
			try {
				String jsonRunRequest = FileUtil.readFileAsString(pathToRequest);
				RunRequest runRequest = new RunRequest();
				runRequest.fromJsonString(jsonRunRequest);
				Map<String, Result> resultMap = cc.blockingRun(runRequest);
				cc.assertErrorFreeRun(resultMap);
			} catch (Exception ex) {
				logger.error("Error during " + pathToRequest + ":", ex);
				throw ex;
			}
		}
	}


	@Test
	public void testLocalBenchmark() throws Exception {
		CoreClient cc = getLocalClient();
		runBenchmark(cc);
		System.gc();
		Thread.sleep(1000);
		/*
			Check for potential memory leak:
		 */
		Assert.assertTrue(10 > localClient.getClientExecutor().getWorkerPool().futueListSize());
	}

	@Test
	public void testHttpBenchmark() throws Exception {
		CoreClient cc = getHttpClient();
		runBenchmark(cc);
		System.gc();
		Thread.sleep(1000);
		/*
			Check for potential memory leak:
		 */
		Assert.assertTrue(10 >  httpClient.getClientExecutor().getWorkerPool().futueListSize());
		Assert.assertTrue(10 >  executor2.getBasisExecutor().getWorkerPool().futueListSize());
		Assert.assertTrue(10 >  executor1.getBasisExecutor().getWorkerPool().futueListSize());

	}




	public void runBenchmark(CoreClient cc) throws Exception {
		List<String> runningRequestsIds = new ArrayList<>();
		Map<String, RunRequest> runRequests = new HashMap<>();
		for (String pathToRequest : FileUtil.listAllFilesInDir(rscPath, "(.*?)\\.json$")) {
			pathToRequest = rscPath + pathToRequest;
			logger.debug("Running execution from: {}", pathToRequest);
			try {
				String jsonRunRequest = FileUtil.readFileAsString(pathToRequest);
				RunRequest runRequest = new RunRequest();
				runRequest.fromJsonString(jsonRunRequest);
				runRequests.put(runRequest.getRequestID(), runRequest);
			} catch (Exception ex) {
				logger.error("Error during parsing: " + pathToRequest + ":", ex);
			}
		}
		int reruns = 100;

		List<Map<String, Result>> results = new ArrayList<>();
		for (int i = 0; i < reruns; i++) {
			for (String requestId : runRequests.keySet()) {
				RunRequest runRequest = runRequests.get(requestId);
				try {
					String runId = requestId + i;
					runRequest.setRequestId(runId);
					CoreClient.MapResultConsumer result = new CoreClient.MapResultConsumer();
					results.add(result);
					cc.run(runRequest, result);
					runningRequestsIds.add(runId);
					logger.debug("Added request Id {}", requestId);
					Thread.sleep(10);
				} catch (Exception ex) {
					logger.error("Error during execution"  + ": ", ex);
				}
			}
		}
		int requestCount = runningRequestsIds.size();
		int percentreached = 0;
		logger.info("{} request many requests have been sent.", requestCount);
		for (int i = 0; i < requestCount; i++) {
			cc.join(runningRequestsIds.get(i), false);
			Map<String, Result> resultMap = results.get(i);
			cc.assertErrorFreeRun(resultMap);
			int currentpercent = ((int)(100. * ((double)i)/((double)requestCount)));

			if(currentpercent > percentreached){
				logger.info("Reached {}%", currentpercent);
				percentreached = currentpercent;
			}
		}
	}

	public void test() {
		HashMap<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("a", new PrimitiveDataField(10));
		RunRequest runRequest = new RunRequest("rId", "composition blabla", new ResolvePolicy(), inputs);
		System.out.println(runRequest.toJsonString());
	}


	public static ClassesConfig getTestClassConfig() {
		ClassesConfig classesConfig = new ClassesConfig();
		classesConfig.appendConfigFromJsonStrings(FileUtil.readResourceAsString("config/demo-classconf.json"));
		return classesConfig;
	}

    public static OnthologicalTypeConfig getTestTypeConfig() {
		OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
		typeConfig.appendConfigFromJsonStrings(FileUtil.readResourceAsString("config/demo-typeconf.json"),
				FileUtil.readResourceAsString("config/builtin-typeconf.json"));
		return typeConfig;
	}

	private static CoreClient getLocalClient() {
		return localClient;
	}

	private static CoreClient getHttpClient() {
		return httpClient;
	}
}
