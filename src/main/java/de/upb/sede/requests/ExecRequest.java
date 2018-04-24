package de.upb.sede.requests;

import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;

public class ExecRequest extends Request {
	private Optional<String> compositionGraph;

	public ExecRequest() {
		this.compositionGraph = Optional.empty();
	}

	public ExecRequest(String requestId, String clientHost, String compositionGraph) {
		super(requestId, clientHost);
		this.compositionGraph = Optional.of(compositionGraph);
	}

	public boolean hasCompositionGraph() {
		return this.compositionGraph.isPresent();
	}

	public String getCompositionGraph() {
		assert hasCompositionGraph();
		return compositionGraph.get();
	}

	public JSONObject toJSON() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("graph", getCompositionGraph());
		return jsonObject;
	}

	public void fromJSON(Map<String, Object> data) {
		super.fromJson(data);
		this.compositionGraph = Optional.ofNullable((String) data.get("graph"));
	}
}
