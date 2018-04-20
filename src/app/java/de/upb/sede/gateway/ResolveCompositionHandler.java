package de.upb.sede.gateway;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.upb.sede.composition.gc.ClientInfo;
import de.upb.sede.composition.gc.ExecutorCoordinator;
import de.upb.sede.composition.gc.GraphConstruction;
import de.upb.sede.composition.gc.ResolveInfo;
import de.upb.sede.composition.graphs.CompositionGraph;
import de.upb.sede.composition.graphs.serialization.GraphJsonSerializer;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.webinterfaces.server.StringServerResponse;

public class ResolveCompositionHandler extends StringServerResponse {

	private final static Logger logger = Logger.getLogger("Composition Resolve");
	private final ExecutorCoordinator coordinator;
	private final ClassesConfig classesConfig;

	public ResolveCompositionHandler(ExecutorCoordinator coordinator, ClassesConfig classesConfig) {
		super();
		this.coordinator = coordinator;
		this.classesConfig = classesConfig;

	}

	@Override
	public String receive(String jsonResolveRequest) {
		logger.debug("Received executor registration.");
		JSONParser parser = new JSONParser();
		/*
		 * TODO: Do validation before parsing.
		 */
		Map<String, Object> resolveDatamap;
		try {
			resolveDatamap = (Map<String, Object>) parser.parse(jsonResolveRequest);
		} catch (Exception e) {
			logger.error("The body of the resolve request cannot be parsed as JSON: " + e.getMessage(), e);
			return "Parse error: " + e.getMessage();
		}
		/*
		 * gather all the information to resolve the composition:
		 */
		ResolveRequest resolveRequest = new ResolveRequest();
		resolveRequest.fromJsonString(jsonResolveRequest);
		ResolveInfo resolveInfo = resolveInfoFromRequest(resolveRequest);
		/*
		 * Resolve the composition by calculating the client graph:
		 */
		CompositionGraph clientGraph = GraphConstruction.resolveClientGraph(resolveRequest.getComposition(), resolveInfo);
		
		/*
		 * Serializae the graph to json:
		 */
		GraphJsonSerializer gjs = new GraphJsonSerializer();
		JSONObject jsonClientGraph = gjs.toJson(clientGraph);
		
		/*
		 * Return the client graph as the body of the answer:
		 */
		return jsonClientGraph.toJSONString();
	}
	
	/**
	 * Builds and returns a new instance of ResolveInfo from the given ResolveRequest.
	 */
	public ResolveInfo resolveInfoFromRequest(ResolveRequest resolveRequest) {
		ResolveInfo info = new ResolveInfo();
		info.setClassesConfiguration(classesConfig);
		info.setExecutorCoordinator(coordinator);
		info.setResolvePolicy(resolveRequest.getPolicy());
		info.setInputFields(resolveRequest.getInputFields());
		info.setClientInfo(new ClientInfo(resolveRequest.getClientHost()));
		return info;
	}

}
