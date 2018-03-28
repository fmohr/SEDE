package de.upb.sede.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * All instances of request should be immutable.
 */
public abstract class Request {
	protected final static String UNDEFINED_REQUESTID = "NO_RID";
	protected final static String UNDEFINED_CLIENTHOST = "NO_CLIENTHOST";
	protected final static String UNDEFINED_COMPOSITION = "NO_COMPOSITION";
	protected final static String UNDEFINED_COMPOSITIONGRAPH = "NO_COMPOSITIONGRAPH";
	protected final static String UNDEFINED_POLICY = "NO_POLICY";
	protected final static Map<String, Object> UNDEFINED_VARIABLES = new HashMap<>();

	protected String requestId;
	protected String clientHost;

	public Request() {
		this.requestId = UNDEFINED_REQUESTID;
		this.clientHost = UNDEFINED_CLIENTHOST;
	}

	public Request(String requestId, String clientHost) {
		this.requestId = requestId;
		this.clientHost = clientHost;
	}

	public abstract Request withRequestId(String requestId);

	public abstract Request withClientHost(String clientHost);

	public boolean hasRequestId() {
		return this.requestId != UNDEFINED_REQUESTID;
	}

	public boolean hasclientHost() {
		return this.clientHost != UNDEFINED_CLIENTHOST;
	}

	public String getRequestID() {
		assert hasRequestId();
		return requestId;
	}

	public String getClientHost() {
		assert hasclientHost();
		return clientHost;
	}
}