package de.upb.sede.composition.graphs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

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
import de.upb.sede.util.GraphToDotNidi;

public class Demo_Resolve {

	
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
//			GatewayResolution resolution = gateway.resolve(resolveRequest);
//			
//			CompositionGraph clientGraph = GJS.fromJson((Map<Object, Object>) JP.parse(resolution.getCompositionGraph()));
//			resolvedGraphs.put(resolveRequest.getClientHost(), clientGraph);
//			for(BaseNode node : GraphTraversal.iterateNodesWithClassname(clientGraph, SendGraphNode.class.getSimpleName())){
//				SendGraphNode graphNode = (SendGraphNode)node;
//				CompositionGraph execGraph = GJS.fromJson((Map<Object, Object>) JP.parse(graphNode.getGraph()));
//				resolvedGraphs.put(graphNode.getExecutorsAddress(), execGraph);
//			}

			Map<String, CompositionGraph> resolvedGraphs = new LinkedHashMap<>();
			GraphConstruction gc = gateway.constructGraphs(resolveRequest);
			String clientId =  resolveRequest.getClientExecutorRegistration().getId();
			resolvedGraphs.put(clientId, gc.getResolvedClientGraph());
			for(Execution exec : gc.getExecutions()) {
				if(exec.getExecutor().getExecutorId().equals(clientId)) {
					continue;
				}
				resolvedGraphs.put(exec.getExecutor().getExecutorId(), exec.getGraph());
			}
			
			

			for(String hostname : resolvedGraphs.keySet()) {
				String svgGraph = GraphToDotNidi.getSVGForGraph(resolvedGraphs.get(hostname));
				String file = outputFolder + ".reso/" + hostname + ".svg";
				FileUtil.writeStringToFile(file, svgGraph);
			}

			String svgGraph = GraphToDotNidi.getSVGForGraphs(resolvedGraphs, gc.getTransmissionGraph());
			String file = outputFolder + ".reso/all.svg";
			FileUtil.writeStringToFile(file, svgGraph);
			
		} catch (Exception e) {
			logger.error("Error during " + outputFolder + ":", e);
//			System.err.println("Error during " + outputFolder + ": \n\t" + e.getMessage() + "\nStacktrace: ");
//			e.printStackTrace(System.err);
		}
	}
	
	
	
	private ResolveRequest fromFile(String filename) {
		ResolveRequest request = new ResolveRequest();
		request.fromJsonString(FileUtil.readFileAsString(rscPath + filename));
		return request;
	}
}
