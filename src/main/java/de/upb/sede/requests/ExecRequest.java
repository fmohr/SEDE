
package de.upb.sede.requests;

/**
 * Immutable
 */
public class ExecRequest extends Request {

    private final Body body;

    private final static String Undefined_RequestId = "NO_RID";
    private final String requestId;

    private final static String Undefined_ClientId = "NO_CID";
    private final String clientId;

    private final static String[] Empfy_HostSet = {};
    private final String[] clientHostSet;

    private ExecRequest(Body body) {
        this.body = body;
        request
    }

    public Body getBody() {
        return body;
    }

	@Override
	public String getRequestId() {

		return requestId;
	}

	@Override
	public String getclientId() {

		return clientId;
	}

	@Override
	public String[] getClientHostSet() {
        
		return clientHostSet;
	}
}