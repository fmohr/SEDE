package de.upb.sede.requests;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.json.simple.JSONObject;

public class DataPutRequest extends Request {

	private final String fieldname;
	private final Object data;
	

	public DataPutRequest(String requestId, String clientHost, String fieldname, Object data) {
		super(requestId, clientHost);
		this.fieldname = fieldname;
		this.data = data;
	}

	public String getFieldname() {
		return fieldname;
	}
	
	public Object getData() {
		return data;
	}
	
	public JSONObject toJson() {
		throw new RuntimeException("Data put request cannot be transformed to json with this method. ");
	}
	
	public void fromJson(Map<String, Object> data) {
		throw new RuntimeException("Data put request cannot be transformed to json with this method. ");
	}
	

}
