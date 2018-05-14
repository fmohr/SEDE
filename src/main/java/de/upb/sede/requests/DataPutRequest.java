package de.upb.sede.requests;

import java.util.Map;

import de.upb.sede.core.SEDEObject;
import org.json.simple.JSONObject;

public class DataPutRequest extends Request {

	private final String fieldname;
	private final SEDEObject data;

	public DataPutRequest(String requestId, String fieldname, SEDEObject data) {
		super(requestId);
		this.fieldname = fieldname;
		this.data = data;
	}

	public String getFieldname() {
		return fieldname;
	}

	public SEDEObject getData() {
		return data;
	}

	public JSONObject toJson() {
		throw new RuntimeException("Data put request cannot be transformed to json with this method. ");
	}

	public void fromJson(Map<String, Object> data) {
		throw new RuntimeException("Illegal method");
	}

}
