package de.upb.sede.requests;

import java.util.*;
import java.util.stream.Collectors;

import de.upb.sede.core.SEDEObject;
import de.upb.sede.util.Maps;
import org.json.simple.JSONObject;

import de.upb.sede.requests.resolve.ResolvePolicy;

public class RunRequest extends Request {

	private Optional<String> composition;
	private Optional<ResolvePolicy> policy;
	private Optional<Map<String, SEDEObject>> inputs;

	public RunRequest() {
		this.composition = Optional.empty();
		this.policy = Optional.empty();
		this.inputs = Optional.empty();

	}


	public RunRequest(String composition, ResolvePolicy policy,
					  Map<String, SEDEObject> inputs) {
		this(generateRequestId(), composition, policy, inputs);
	}

	public RunRequest(String requestId, String composition, ResolvePolicy policy,
					  Map<String, SEDEObject> inputs) {
		super(requestId);
		this.composition = Optional.of(composition);
		this.policy = Optional.of(policy);
		this.inputs = Optional.of(inputs);
	}

	public boolean hasComposition() {
		return this.composition.isPresent();
	}

	public boolean hasPolicy() {
		return this.policy.isPresent();
	}

	public boolean hasVariables() {
		return this.inputs.isPresent();
	}

	public static String generateRequestId() {
		return UUID.randomUUID().toString();
	}

	public String getComposition() {
		assert hasComposition();
		return composition.get();
	}

	public ResolvePolicy getPolicy() {
		assert hasPolicy();
		return policy.get();
	}

	public Map<String, SEDEObject> getInputs() {
		assert hasVariables();
		return inputs.get();
	}

	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("composition", getComposition());
		jsonObject.put("policy", getPolicy().toJson());
		JSONObject jsonInputs = new JSONObject();
		Maps.translate(getInputs(), jsonInputs, SEDEObject::toJson);
		jsonObject.put("inputs", jsonInputs);
		return jsonObject;
	}

	@SuppressWarnings("unchecked")
	public void fromJson(Map<String, Object> data) {
		if(!data.containsKey("requestId")) {
			/*
				Run request doesn't define an id. Generate a new one:
			 */
			data.put("requestId", generateRequestId());
		}
		super.fromJson(data);
		Object compositionEntry = data.get("composition");
		if(compositionEntry instanceof String) {
			this.composition = Optional.of((String) compositionEntry);
		} else if(compositionEntry instanceof List) {
			this.composition = Optional.of(((List<String>) compositionEntry).stream().collect(Collectors.joining(";")));
		} else {
			throw new RuntimeException(compositionEntry.getClass().getName());
		}
		ResolvePolicy policy = new ResolvePolicy();
		if (data.containsKey("policy")) { // if policy is defined overwrite standard policy:
			policy.fromJson((Map<String, Object>) data.get("policy"));
		}
		this.policy = Optional.of(policy);
		if (data.containsKey("inputs")) {
			JSONObject jsonInputs = (JSONObject) data.get("inputs");
			Map<String, SEDEObject> inputs = new HashMap<>();
			Maps.translate(jsonInputs, inputs, SEDEObject::constructFromJson);
			this.inputs = Optional.of(inputs);
		} else {
			// if no inputs is defined set it to an empty map.
			this.inputs = Optional.of(Collections.EMPTY_MAP);
		}
	}

	/**
	 * For testing purposes.
	 */
	public void setRequestId(String requestId) {
		this.requestId = Optional.of(requestId);
	}
}
