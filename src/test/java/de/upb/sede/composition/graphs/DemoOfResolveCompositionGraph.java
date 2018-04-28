package de.upb.sede.composition.graphs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.upb.sede.composition.graphs.nodes.BaseNode;
import de.upb.sede.composition.graphs.nodes.SendGraphNode;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.gateway.ClientInfo;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.GraphToDot;

public class DemoOfResolveCompositionGraph {

	
	private static final String rscPath = "testrsc/resolve-requests/"; 
	
	private static Gateway gateway;
	private static final GraphJsonSerializer GJS = new GraphJsonSerializer();
	private static final JSONParser JP = new JSONParser();
	
	private static final Logger logger = LogManager.getLogger();
	

	@BeforeClass
	public static void setupGateway() {
		gateway = new Gateway(getTestClassConfig(), getTestTypeConfig());
		ExecutorRegistration registration1 = new ExecutorRegistration("exec_1", Arrays.asList("java"),
				Arrays.asList("demo.math.Addierer"));
		ExecutorRegistration registration2 = new ExecutorRegistration("exec_2", Arrays.asList("java"),
				Arrays.asList("demo.math.Gerade"));
		gateway.register(registration1);
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
			logger.info("Demonstrating resolve algorithm on request from: {}", pathToRequest);
			resolveToDot(pathToRequest);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void resolveToDot(String filenameOfRequest) {
		try {
			ResolveRequest resolveRequest = fromFile(filenameOfRequest);
			GatewayResolution resolution = gateway.resolve(resolveRequest);
			Map<String, CompositionGraph> resolvedGraphs = new HashMap<>();
			CompositionGraph clientGraph = GJS.fromJson((Map<Object, Object>) JP.parse(resolution.getCompositionGraph()));
			resolvedGraphs.put(resolveRequest.getClientHost(), clientGraph);
			for(BaseNode node : GraphTraversal.iterateNodesWithClassname(clientGraph, SendGraphNode.class.getSimpleName())){
				SendGraphNode graphNode = (SendGraphNode)node;
				CompositionGraph execGraph = GJS.fromJson((Map<Object, Object>) JP.parse(graphNode.getGraph()));
				resolvedGraphs.put(graphNode.getExecutorsAddress(), execGraph);
			}
			for(String hostname : resolvedGraphs.keySet()) {
				String svgGraph = GraphToDot.getSVGForGraph(resolvedGraphs.get(hostname));
				String file = rscPath + filenameOfRequest.substring(0, filenameOfRequest.length()-5) + ".reso/" + hostname + ".svg";
				FileUtil.writeStringToFile(file, svgGraph);
			}

//			String svgGraph = GraphToDot.getSVGForGraphs(resolvedGraphs);
//			String file = rscPath + filenameOfRequest.substring(0, filenameOfRequest.length()-5) + ".all.svg";
//			FileUtil.writeStringToFile(file, svgGraph);
			
		} catch (Exception e) {
			logger.error("Error during " + filenameOfRequest + ":", e);
//			System.err.println("Error during " + filenameOfRequest + ": \n\t" + e.getMessage() + "\nStacktrace: ");
//			e.printStackTrace(System.err);
		}
	}
	
	
	
	private ResolveRequest fromFile(String filename) {
		ResolveRequest request = new ResolveRequest();
		request.fromJsonString(FileUtil.readFileAsString(rscPath + filename));
		return request;
	}
}
