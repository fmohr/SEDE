package de.upb.sede.requests;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataPutRequest {

	private final static String UNDEFINED_DataPutRequestID = "NO_RID";
	private final String DataPutRequestId;

	private final static String UNDEFINED_CLIENTHOST = "NO_CLIENTHOST";
	private final String clientHost;

	private final static Map<String, Object> UNDEFINED_VARIABLES = new HashMap<>();
	private final Map<String, Object> variables;

	public DataPutRequest() {
		this.DataPutRequestId = UNDEFINED_DataPutRequestID;
		this.clientHost = UNDEFINED_CLIENTHOST;
		this.variables = UNDEFINED_VARIABLES;

	}

	private DataPutRequest(String DataPutRequestId, String clientHost, Map<String, Object> variables) {
		this.DataPutRequestId = DataPutRequestId;
		this.clientHost = clientHost;
		this.variables = variables;
	}

	/*
	 * With methods
	 */
	public DataPutRequest withDataPutRequestId(String DataPutRequestId) {
		return new DataPutRequest(Objects.requireNonNull(DataPutRequestId), clientHost, variables);
	}

	public DataPutRequest withClientHost(String clientHost) {
		return new DataPutRequest(DataPutRequestId, Objects.requireNonNull(clientHost), variables);
	}

	public DataPutRequest withVariables(Map<String, Object> variables) {
		return new DataPutRequest(DataPutRequestId, clientHost, Objects.requireNonNull(variables));
	}

	/*
	 * has methods
	 */

	public boolean hasDataPutRequestId() {
		return this.DataPutRequestId != UNDEFINED_DataPutRequestID;
	}

	public boolean hasclientHost() {
		return this.clientHost != UNDEFINED_CLIENTHOST;
	}

	public boolean hasVariables() {
		return this.variables != UNDEFINED_VARIABLES;
	}

	/*
	 * get methods
	 */

	public String getDataPutRequestID() {
		assert hasDataPutRequestId();
		return DataPutRequestId;
	}

	public String getClientHost() {
		assert hasclientHost();
		return clientHost;
	}

	public Map<String, Object> getVariables() {
		assert hasVariables();
		return variables;
	}
}
