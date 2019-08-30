package de.upb.sede.requests.resolve;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;

import de.upb.sede.util.JsonSerializable;

public class GatewayResolution implements JsonSerializable {

	private Optional<String> compositionGraph;
	private Optional<List<String>> returnFields;
	private Optional<String> dotSvg;

	public GatewayResolution(String compositionGraph, List<String> returnFields) {
		this.compositionGraph = Optional.of(compositionGraph);
		this.returnFields = Optional.of(returnFields);
		this.dotSvg = Optional.empty();
	}

	public GatewayResolution() {
		this.compositionGraph = Optional.empty();
		this.returnFields = Optional.empty();
		this.dotSvg = Optional.empty();
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

	public final void setDotSvg(String dotSvg) {
		this.dotSvg = Optional.of(dotSvg);
	}

	public final String getDotSvg() {
		return dotSvg.get();
	}


	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("graph", getCompositionGraph());
		jsonObject.put("return-fields", getReturnFields());
		if(dotSvg.isPresent()){
			jsonObject.put("dotsvg", dotSvg.get());
		}
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		this.compositionGraph = Optional.of((String) data.get("graph"));
		this.returnFields = Optional.of((List<String>)data.get("return-fields"));
		if(data.containsKey("dotsvg")){
			this.dotSvg = Optional.of((String)data.get("dotsvg"));
		}
	}

}
