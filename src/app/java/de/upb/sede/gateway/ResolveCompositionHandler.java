package de.upb.sede.gateway;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import de.upb.sede.composition.gc.ExecutorCoordinator;
import de.upb.sede.composition.gc.ResolvePolicy;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.requests.ResolveRequest;
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
		String requestId = (String) resolveDatamap.get("requestId");
		String clientHost = (String) resolveDatamap.get("clientHost");
		String fmComposition = (String) resolveDatamap.get("fmComposition");
		ResolvePolicy resolvePolicy = ResolvePolicy.fromJson(resolveDatamap.get("policy"));
		Map<String, String> inputTypeMap = (Map<String, String>) resolveDatamap.get("inputTypeMap");

		ResolveRequest resolveRequest = new ResolveRequest().withClientHost(clientHost).withComposition(fmComposition)
				.withInputTypeMap(inputTypeMap).withRequestId(requestId).withPolicy(resolvePolicy);
		JSONObject resolvedGraph = resolveGraph(resolveRequest);
		return "";
	}
	
	public JSONObject resolveGraph(ResolveRequest resolveRequest) {
		return null; // TODO
	}

}
