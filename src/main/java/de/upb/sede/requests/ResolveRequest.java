package de.upb.sede.requests;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import de.upb.sede.composition.gc.ResolvePolicy;

public class ResolveRequest extends Request {
	private final String composition;
	private final ResolvePolicy policy;
	private final Map<String, String> inputTypeMap; 

	public ResolveRequest() {
		this.composition = UNDEFINED_COMPOSITION;
		this.policy = UNDEFINED_POLICY;
		this.inputTypeMap = Collections.EMPTY_MAP;
	}

	private ResolveRequest(String requestId, String clientHost, String composition, ResolvePolicy policy, Map<String, String> inputTypeMap) {
		super(requestId, clientHost);
		this.composition = composition;
		this.policy = policy;
		this.inputTypeMap = inputTypeMap;
	}

	@Override
	public ResolveRequest withRequestId(String requestId) {
		return new ResolveRequest(Objects.requireNonNull(requestId), clientHost, composition, policy, inputTypeMap);
	}

	@Override
	public ResolveRequest withClientHost(String clientHost) {
		return new ResolveRequest(requestId, Objects.requireNonNull(clientHost), composition, policy, inputTypeMap);
	}

	public ResolveRequest withComposition(String composition) {
		return new ResolveRequest(requestId, clientHost, Objects.requireNonNull(composition), policy, inputTypeMap);
	}

	public ResolveRequest withPolicy(ResolvePolicy policy) {
		return new ResolveRequest(requestId, clientHost, composition, Objects.requireNonNull(policy), inputTypeMap);
	}
	
	public ResolveRequest withInputTypeMap(Map<String, String> inputTypeMap) {
		return new ResolveRequest(requestId, clientHost, composition, policy, Objects.requireNonNull(inputTypeMap));
	}

	public boolean hasComposition() {
		return this.composition != UNDEFINED_COMPOSITION;
	}

	public boolean hasPolicy() {
		return this.policy != UNDEFINED_POLICY;
	}

	public String getComposition() {
		assert hasComposition();
		return composition;
	}

	public ResolvePolicy getPolicy() {
		assert hasPolicy();
		return policy;
	}
}
