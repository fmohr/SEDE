package demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.ExecPlan;
import de.upb.sede.composition.graphs.GraphConstruction;
import de.upb.sede.requests.resolve.GatewayResolution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.FileUtil;

public class IntegrationTest_Resolve {

	
	private static final String rscPath = "testrsc/resolve-requests/"; 
	
	private static Gateway gateway;
	private static final GraphJsonSerializer GJS = new GraphJsonSerializer();
	private static final JSONParser JP = new JSONParser();
	
	private static final Logger logger = LogManager.getLogger();
	

	@BeforeClass
	public static void setupGateway() {
		gateway = new Gateway(getTestClassConfig(), getTestTypeConfig());

		Map<String, String> contactInfo = new HashMap<>();
		contactInfo.put("id", "exec_1");
		ExecutorRegistration registration1 = new ExecutorRegistration(contactInfo, Arrays.asList("java"),
				Arrays.asList("demo.math.Addierer"));
		gateway.register(registration1);

		contactInfo = new HashMap<>();
		contactInfo.put("id", "exec_2");
		ExecutorRegistration registration2 = new ExecutorRegistration(contactInfo, Arrays.asList("java"),
				Arrays.asList("demo.math.Gerade"));
		gateway.register(registration2);
	}
	
	@Before
	public void clearRequest() {
	}

	private static ClassesConfig getTestClassConfig() {
		return new ClassesConfig("testrsc/config/demo-classconf.json");
	}

	private static OnthologicalTypeConfig getTestTypeConfig() {
		return new OnthologicalTypeConfig("testrsc/config/demo-typeconf.json");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void demoAll() {
		for(String pathToRequest : FileUtil.listAllFilesInDir(rscPath, "(.*?)\\.json$")) {
			logger.info("Resolving request from: {}", pathToRequest);
			resolveToDot(pathToRequest);
		}
	}
	public void resolveToDot(String filenameOfRequest) {
		ResolveRequest resolveRequest = fromFile(filenameOfRequest);
		resolveToDot(resolveRequest, gateway, rscPath + filenameOfRequest.substring(0, filenameOfRequest.length()-5));
	}

	public static void resolveToDot(ResolveRequest resolveRequest, Gateway gateway, String outputFolder) {
		try {
			resolveRequest.getPolicy().setToReturnDotGraph(true);
			GatewayResolution resolution = gateway.resolve(resolveRequest);

			String svgGraph = resolution.getDotSvg();
			String svgPath = outputFolder + ".resolution.svg";
			FileUtil.writeStringToFile(svgPath, svgGraph);
			
		} catch (Exception e) {
			logger.error("Error during " + outputFolder + ":", e);
		}
	}
	
	
	
	private ResolveRequest fromFile(String filename) {
		ResolveRequest request = new ResolveRequest();
		request.fromJsonString(FileUtil.readFileAsString(rscPath + filename));
		return request;
	}
}
