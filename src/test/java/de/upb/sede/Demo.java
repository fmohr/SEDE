package de.upb.sede;

import de.upb.sede.client.HttpCoreClient;
import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.Demo_Resolve;
import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.InstructionNode;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.core.CoreClient;
import de.upb.sede.core.SEDEObject;
import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.exec.Executor;
import de.upb.sede.exec.ExecutorConfiguration;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.gateway.Gateway;
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

public class Demo {
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


	@Test public void demoLocalRun() {
		Executor clientExecutor = new Executor();
		/* supports everything */
		clientExecutor.getExecutorConfiguration().getSupportedServices().addAll(Arrays.asList("demo.math.Addierer", "demo.math.Gerade"));
		clientExecutor.getExecutorConfiguration().setExecutorId("Core Client");
		CoreClient cc = new CoreClient(clientExecutor, gateway::resolve);

		for(String pathToRequest : FileUtil.listAllFilesInDir(rscPath, "(.*?)\\.json$")) {
			pathToRequest = rscPath + pathToRequest;
			logger.info("Running execution from: {}", pathToRequest);
			try{
				String jsonRunRequest = FileUtil.readFileAsString(pathToRequest);
				RunRequest runRequest = new RunRequest();
				runRequest.fromJsonString(jsonRunRequest);
				ResolveRequest resolveRequest = cc.runToResolve(runRequest, "id123");

				Demo_Resolve.resolveToDot(resolveRequest, gateway, pathToRequest);
				String requestId = cc.run(runRequest, null);
				cc.join(requestId, false);
			} catch(Exception ex) {
				logger.error("Error during " + pathToRequest + ":", ex);
			}
		}
	}
	
	final static int reruns = 50;

	@Test public void demoHttpRun() {
		/* supports everything */

		CoreClient  cc= new HttpCoreClient("localhost", 9003, "localhost", 9000);
		cc.getClientExecutor().getExecutorConfiguration().setExecutorId("Core Client");
		List<String> runningRequestsIds = new ArrayList<>();
		for (int i = 0; i < reruns; i++)
			for(String pathToRequest : FileUtil.listAllFilesInDir(rscPath, "(.*?)\\.json$")) {
				pathToRequest = rscPath + pathToRequest;
				logger.info("Running execution from: {}", pathToRequest);
				try{
					String jsonRunRequest = FileUtil.readFileAsString(pathToRequest);
					RunRequest runRequest = new RunRequest();
					runRequest.fromJsonString(jsonRunRequest);
	//				ResolveRequest resolveRequest = cc.runToResolve(runRequest, "id123");

	//				Demo_Resolve.resolveToDot(resolveRequest, gateway, pathToRequest + ".http");
					String requestId = cc.run(runRequest, null);
					runningRequestsIds.add(requestId);
				} catch(Exception ex) {
					logger.error("Error during " + pathToRequest + ":", ex);
				}
//				System.out.println("Reached " + i);
			}
		for (int i = 0; i < reruns; i++) {
			cc.join(runningRequestsIds.get(i), false);
			if(((int)(100. * ((double)i)/((double)reruns))) %5 == 0){
				logger.info("Reached {}%", ((int)(100. * ((double)i)/((double)reruns))));
			}
		}	
//		for(String unfinishedRequest : runningRequestsIds) {
//			cc.join(unfinishedRequest, false);
//		}
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
