package de.upb.sede.requests;

import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;

import de.upb.sede.requests.resolve.ResolvePolicy;

public class RunRequest extends Request {
	
	private Optional<String> composition;
	private Optional<ResolvePolicy> policy;

	private Optional<Map<String, Object>> variables;

	public RunRequest() {
		this.composition = Optional.empty();
		this.policy = Optional.empty();
		this.variables = Optional.empty();

	}

	public RunRequest(String requestId, String clientHost, String composition, ResolvePolicy policy,
			Map<String, Object> variables) {
		super(requestId, clientHost);
		this.composition = Optional.of(composition);
		this.policy = Optional.of(policy);
		this.variables = Optional.of(variables);
	}


	public boolean hasComposition() {
		return this.composition.isPresent();
	}

	public boolean hasPolicy() {
		return this.policy.isPresent();
	}

	public boolean hasVariables() {
		return this.variables.isPresent();
	}

	public String getComposition() {
		assert hasComposition();
		return composition.get();
	}

	public ResolvePolicy getPolicy() {
		assert hasPolicy();
		return policy.get();
	}

	public Map<String, Object> getVariables() {
		assert hasVariables();
		return variables.get();
	}

	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("composition", composition);
		jsonObject.put("policy", getPolicy().toJson());
		// TODO serialize variables with the marshal system.
		return jsonObject;
	}
	
	@SuppressWarnings("unchecked")
	public void fromJson(Map<String, Object> data) {
		super.fromJson(data);
		composition = Optional.of((String) data.get("composition"));
		ResolvePolicy policy = new ResolvePolicy();
		if(data.containsKey("policy")) { // if policy defined overwrite standard policy:
			policy.fromJson((Map<String, Object>) data.get("policy")); 
		}
		this.policy = Optional.of(policy);
		// TODO deserialize variables using the marshal system.
	}
}
