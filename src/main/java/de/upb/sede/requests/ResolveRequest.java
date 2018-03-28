package de.upb.sede.requests;

import java.util.Objects;

public class ResolveRequest {

	private final static String UNDEFINED_ResolveRequestID = "NO_RID";
	private final String ResolveRequestId;

	private final static String UNDEFINED_CLIENTHOST = "NO_CLIENTHOST";
	private final String clientHost;

	private final static String UNDEFINED_COMPOSITION = "NO_COMPOSITION";
	private final String composition;

	private final static String UNDEFINED_POLICY = "NO_POLICY";
	private final String policy;

	public ResolveRequest() {
		this.ResolveRequestId = UNDEFINED_ResolveRequestID;
		this.clientHost = UNDEFINED_CLIENTHOST;
		this.composition = UNDEFINED_COMPOSITION;
		this.policy = UNDEFINED_POLICY;

	}

	private ResolveRequest(String ResolveRequestId, String clientHost, String composition, String policy) {
		this.ResolveRequestId = ResolveRequestId;
		this.clientHost = clientHost;
		this.composition = composition;
		this.policy = policy;
	}

	/*
	 * With methods
	 */
	public ResolveRequest withResolveRequestId(String ResolveRequestId) {
		return new ResolveRequest(Objects.requireNonNull(ResolveRequestId), clientHost, composition, policy);
	}

	public ResolveRequest withClientHost(String clientHost) {
		return new ResolveRequest(ResolveRequestId, Objects.requireNonNull(clientHost), composition, policy);
	}

	public ResolveRequest withComposition(String composition) {
		return new ResolveRequest(ResolveRequestId, clientHost, Objects.requireNonNull(composition), policy);
	}

	public ResolveRequest withPolicy(String policy) {
		return new ResolveRequest(ResolveRequestId, clientHost, composition, Objects.requireNonNull(policy));
	}

	/*
	 * has methods
	 */

	public boolean hasResolveRequestId() {
		return this.ResolveRequestId != UNDEFINED_ResolveRequestID;
	}

	public boolean hasclientHost() {
		return this.clientHost != UNDEFINED_CLIENTHOST;
	}

	public boolean hasComposition() {
		return this.composition != UNDEFINED_COMPOSITION;
	}

	public boolean hasPolicy() {
		return this.policy != UNDEFINED_POLICY;
	}

	/*
	 * get methods
	 */

	public String getResolveRequestID() {
		assert hasResolveRequestId();
		return ResolveRequestId;
	}

	public String getClientHost() {
		assert hasclientHost();
		return clientHost;
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
