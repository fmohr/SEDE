package de.upb.sede.requests;

import java.util.Objects;

public class ResolveRequest extends Request {
	private final String composition;
	private final String policy;

	public ResolveRequest() {
		this.composition = UNDEFINED_COMPOSITION;
		this.policy = UNDEFINED_POLICY;

	}

	private ResolveRequest(String requestId, String clientHost, String composition, String policy) {
		super(requestId, clientHost);
		this.composition = composition;
		this.policy = policy;
	}

	@Override
	public ResolveRequest withRequestId(String requestId) {
		return new ResolveRequest(Objects.requireNonNull(requestId), clientHost, composition, policy);
	}

	@Override
	public ResolveRequest withClientHost(String clientHost) {
		return new ResolveRequest(requestId, Objects.requireNonNull(clientHost), composition, policy);
	}

	public ResolveRequest withComposition(String composition) {
		return new ResolveRequest(requestId, clientHost, Objects.requireNonNull(composition), policy);
	}

	public ResolveRequest withPolicy(String policy) {
		return new ResolveRequest(requestId, clientHost, composition, Objects.requireNonNull(policy));
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

	public String getPolicy() {
		assert hasPolicy();
		return policy;
	}
}
