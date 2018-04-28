package de.upb.sede.requests.resolve;

import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;

import de.upb.sede.util.JsonSerializable;

public class GatewayResolution implements JsonSerializable {

	private Optional<String> compositionGraph;

	public GatewayResolution(String compositionGraph) {
		this.compositionGraph = Optional.of(compositionGraph);
	}

	public GatewayResolution() {
		this.compositionGraph = Optional.empty();
	}

	public final String getCompositionGraph() {
		return compositionGraph.get();
	}

	public final void setCompositionGraph(String graph) {
		this.compositionGraph = Optional.of(graph);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("graph", compositionGraph);
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		this.compositionGraph = Optional.of((String) data.get("graph"));
	}

}