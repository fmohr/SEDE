package demo;

import de.upb.sede.client.CoreClientHttpServer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.CoreClient;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.exec.Executor;
import de.upb.sede.exec.ExecutorConfiguration;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.gateway.GatewayHttpServer;
import de.upb.sede.requests.RunRequest;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IntegrationTest_Execute {
	private static final Logger logger = LogManager.getLogger();

	private static final String rscPath = "testrsc/run-requests/";

	static ExecutorHttpServer executor1;
	static ExecutorHttpServer executor2;

	static GatewayHttpServer gateway;

	@BeforeClass public static void setup() {
		ExecutorConfiguration config = new ExecutorConfiguration();
		config.getSupportedServices().addAll(Arrays.asList("demo.math.Addierer"));
		config.setExecutorId("Executor 1");
		executor1 = new ExecutorHttpServer(config, "localhost", 9001);

		config = new ExecutorConfiguration();
		config.getSupportedServices().addAll(Arrays.asList("demo.math.Gerade"));
		config.setExecutorId("Executor 2");
		executor2 = new ExecutorHttpServer(config, "localhost", 9002);

		gateway = new GatewayHttpServer(9000, getTestClassConfig(), getTestTypeConfig());

		executor1.registerToGateway("localhost:9000");
		executor2.registerToGateway("localhost:9000");
	}

	@AfterClass public static void shutdown() {
		gateway.shutdown();
		executor1.shutdown();
		executor2.shutdown();
	}



	@Test public void testLocalRun() {
		Executor clientExecutor = new Executor();
		/* supports everything */
		clientExecutor.getExecutorConfiguration().getSupportedServices().addAll(Arrays.asList("demo.math.Addierer", "demo.math.Gerade"));
		clientExecutor.getExecutorConfiguration().setExecutorId("Core Client");
		CoreClient cc = new CoreClient(clientExecutor, gateway::resolve);
		runAllOnce(cc);
	}

	@Test public void testHttpRun() {
		/* supports nothing */
		CoreClientHttpServer cc = new CoreClientHttpServer("localhost", 9004, "localhost", 9000);
		runAllOnce(cc);
		cc.getClientExecutor().shutdown();
	}

	public void runAllOnce(CoreClient cc) {

		for(String pathToRequest : FileUtil.listAllFilesInDir(rscPath, "(.*?)\\.json$")) {
			pathToRequest = rscPath + pathToRequest;
			logger.info("Running execution from: {}", pathToRequest);
			try{
				String jsonRunRequest = FileUtil.readFileAsString(pathToRequest);
				RunRequest runRequest = new RunRequest();
				runRequest.fromJsonString(jsonRunRequest);
				ResolveRequest resolveRequest = cc.runToResolve(runRequest, "id123");

				IntegrationTest_Resolve.resolveToDot(resolveRequest, gateway, pathToRequest);
				String requestId = cc.run(runRequest, null);
				cc.join(requestId, false);
			} catch(Exception ex) {
				logger.error("Error during " + pathToRequest + ":", ex);
			}
		}
	}


	final static int reruns = 50000;

	@Test public void testLocalBenchmark() throws InterruptedException {
		Executor clientExecutor = new Executor();
		/* supports everything */
		clientExecutor.getExecutorConfiguration().getSupportedServices().addAll(Arrays.asList("demo.math.Addierer", "demo.math.Gerade"));
		clientExecutor.getExecutorConfiguration().setExecutorId("Core Client Benchmark");
		CoreClient cc = new CoreClient(clientExecutor, gateway::resolve);
		runBenchmark(cc);
	}

	@Test public void testHttpBenchmark() throws InterruptedException {
		/* supports nothing */
		CoreClientHttpServer cc = new CoreClientHttpServer("localhost", 9003, "localhost", 9000);
		cc.getClientExecutor().getExecutorConfiguration().setExecutorId("Core Client Benchmark");
		runBenchmark(cc);
		cc.getClientExecutor().shutdown();
	}




	public void runBenchmark(CoreClient cc) throws InterruptedException {
		List<String> runningRequestsIds = new ArrayList<>();
		List<RunRequest> runRequests = new ArrayList<>();
		for (String pathToRequest : FileUtil.listAllFilesInDir(rscPath, "(.*?)\\.json$")) {
			pathToRequest = rscPath + pathToRequest;
			logger.debug("Running execution from: {}", pathToRequest);
			try {
				String jsonRunRequest = FileUtil.readFileAsString(pathToRequest);
				RunRequest runRequest = new RunRequest();
				runRequest.fromJsonString(jsonRunRequest);
				runRequests.add(runRequest);
			} catch (Exception ex) {
				logger.error("Error during parsing: " + pathToRequest + ":", ex);
			}
		}
		for (int i = 0; i < reruns; i++) {
			for (RunRequest runRequest : runRequests) {
				try {
					String requestId = cc.run(runRequest, null);
					runningRequestsIds.add(requestId);
					logger.debug("Added request Id {}", requestId);
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
			int currentpercent = ((int)(100. * ((double)i)/((double)requestCount)));

			if(currentpercent > percentreached){
				logger.info("Reached {}%", currentpercent);
				percentreached = currentpercent;
			}
		}
	}

	public void test() {
		HashMap<String, SEDEObject> inputs = new HashMap<>();
		inputs.put("a", new SEDEObject("Number", 10));
		RunRequest runRequest = new RunRequest("rId", "composition blabla", new ResolvePolicy(), inputs);
		System.out.println(runRequest.toJsonString());
	}


	private static ClassesConfig getTestClassConfig() {
		return new ClassesConfig("testrsc/config/demo-classconf.json");
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		return new OnthologicalTypeConfig("testrsc/config/demo-typeconf.json");
	}
}
