package de.upb.sede.requests.resolve;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;

import de.upb.sede.util.JsonSerializable;

public class GatewayResolution implements JsonSerializable {

	private Optional<String> compositionGraph;
	private Optional<List<String>> returnFields;

	public GatewayResolution(String compositionGraph, List<String> returnFields) {
		this.compositionGraph = Optional.of(compositionGraph);
		this.returnFields = Optional.of(returnFields);
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

	public final List<String> getReturnFields(){
		return returnFields.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("graph", getCompositionGraph());
		jsonObject.put("return-fields", getReturnFields());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		this.compositionGraph = Optional.of((String) data.get("graph"));
		this.returnFields = Optional.of((List<String>)data.get("return-fields"));
	}

}