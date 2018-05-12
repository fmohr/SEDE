package de.upb.sede.requests.resolve;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import de.upb.sede.requests.ExecutorRegistration;
import org.json.simple.JSONObject;

import de.upb.sede.composition.FMCompositionParser;
import de.upb.sede.requests.Request;

import javax.swing.text.html.Option;

public class ResolveRequest extends Request {
	private Optional<String> composition;
	private Optional<ResolvePolicy> policy;
	private Optional<InputFields> inputFields;
	private Optional<ExecutorRegistration> clientExecutor;

	public ResolveRequest() {
		this.composition = Optional.empty();
		this.policy = Optional.of(new ResolvePolicy());
		this.inputFields = Optional.empty();
		this.clientExecutor = Optional.empty();
	}

	public ResolveRequest(String requestId, String clientHost, String composition, ResolvePolicy policy,
			InputFields inputFields, ExecutorRegistration clientExecutor) {
		super(requestId, clientHost);
		this.composition = Optional.of(composition);
		this.policy = Optional.of(policy);
		this.inputFields = Optional.of(inputFields);
		this.clientExecutor = Optional.of(clientExecutor);
	}

	public boolean hasComposition() {
		return this.composition.isPresent();
	}

	public boolean hasPolicy() {
		return this.policy.isPresent();
	}

	public String getComposition() {
		assert hasComposition();
		return composition.get();
	}

	public ResolvePolicy getPolicy() {
		assert hasPolicy();
		return policy.get();
	}


	public ExecutorRegistration getClientExecutorRegistration() {
		return clientExecutor.get();
	}

	public InputFields getInputFields() {
		return inputFields.get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("composition", FMCompositionParser.separateInstructions(getComposition()));
		jsonObject.put("policy", getPolicy().toJson());
		jsonObject.put("input-fields", getInputFields().toJson());
		jsonObject.put("client-executor", getClientExecutorRegistration().toJson());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		super.fromJson(data);

		Object compositionEntry =  data.get("composition");

		if(compositionEntry instanceof String) {
			this.composition = Optional.of((String) compositionEntry);
		} else if(compositionEntry instanceof List) {
			this.composition = Optional.of(((List<String>) compositionEntry).stream().collect(Collectors.joining(";")));
		} else {
			throw new RuntimeException("");
		}

		ResolvePolicy resolvePolicy = new ResolvePolicy();
		resolvePolicy.fromJson((Map<String, Object>) data.get("policy"));
		this.policy = Optional.of(resolvePolicy);

		InputFields jsonInputFields = new InputFields();
		jsonInputFields.fromJson((Map<String, Object>) data.get("input-fields"));
		this.inputFields = Optional.of(jsonInputFields);



		ExecutorRegistration clientExec = new ExecutorRegistration();
		clientExec.fromJson((Map<String, Object>) data.get("client-executor"));
		this.clientExecutor = Optional.of(clientExec);
	}

}
