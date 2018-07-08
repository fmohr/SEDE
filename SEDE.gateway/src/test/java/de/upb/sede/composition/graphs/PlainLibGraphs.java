package de.upb.sede.composition.graphs;

import de.upb.sede.composition.graphs.nodes.SendGraphNode;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.requests.ExecRequest;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.util.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public class PlainLibGraphs {

	private static Gateway gateway;
	private static final GraphJsonSerializer GJS = new GraphJsonSerializer();

	private static final Logger logger = LogManager.getLogger();


	@BeforeClass
	public static void setupGateway() {
		gateway = new Gateway(getPlainlibClassConfig(), getPlainlibTypeConfig());

		Map<String, String> contactInfo = new HashMap<>();
		contactInfo.put("id", "python_executor");
		List<String> supportedServices = new ArrayList<>();
		supportedServices.addAll(getPlainlibClassConfig().classesKnown());
		ExecutorRegistration registration1 = new ExecutorRegistration(contactInfo, Arrays.asList("python"),
				supportedServices);
		logger.info(registration1.toJsonString());
		gateway.register(registration1);
	}
	@Test
	public void simplegraph(){
		String composition = "b = plainlib.package1.b.B::__construct({0,1});" +
							 "erg = b::calc({3})";
		InputFields inputFields = new InputFields();
		int execNr = 1;
		ResolveRequest rr = createRR(execNr, composition, inputFields);
		resolve_and_store(rr, execNr);
	}

	public static void resolve_and_store(ResolveRequest rr, int execNr) {
		rr.getPolicy().setToReturnDotGraph(true);
		GatewayResolution resolution = gateway.resolve(rr);
		String pathPrefix= "testrsc/resolutions/plainlib/e_" + execNr;
		FileUtil.writeStringToFile(pathPrefix + ".svg", resolution.getDotSvg());
		String graphString = resolution.getCompositionGraph();
		CompositionGraph graph = GJS.fromJsonString(graphString);
		SendGraphNode sendGraphNode = (SendGraphNode) GraphTraversal.iterateNodesWithClassname(graph, "SendGraphNode").iterator().next();
		graphString = sendGraphNode.getGraph();
		ExecRequest execRequest = new ExecRequest("execution_" + execNr, graphString);
		FileUtil.writeStringToFile(pathPrefix + ".json", execRequest.toJsonString());
	}
	private static ResolveRequest createRR(int execNr, String composition, InputFields inputs) {
		return  new ResolveRequest("execution_" + execNr, composition, new ResolvePolicy(), inputs, getClientRegistration());
	}


	private static ClassesConfig getPlainlibClassConfig() {
		return new ClassesConfig("testrsc/config/plainlib-classconf.json");
	}

	private static OnthologicalTypeConfig getPlainlibTypeConfig() {
		return new OnthologicalTypeConfig("testrsc/config/plainlib-typeconf.json");
	}

	private static ExecutorRegistration getClientRegistration() {
		return ExecutorRegistration.client_registration("client");
	}

}
