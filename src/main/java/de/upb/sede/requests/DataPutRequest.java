package de.upb.sede.requests;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import de.upb.sede.core.SEDEObject;
import org.json.simple.JSONObject;

public class DataPutRequest extends Request {

	private String fieldname;
	private Optional<SEDEObject> data;

	private boolean unavailable;

	public DataPutRequest() {
	}

	public DataPutRequest(String requestId, String fieldname, SEDEObject data) {
		super(requestId);
		this.fieldname = fieldname;
		this.data = Optional.of(data);
		this.unavailable = false;
	}

	private DataPutRequest(String requestId, String fieldname, Optional<SEDEObject> data, boolean dataUnavailable) {
		super(requestId);
		this.fieldname = fieldname;
		this.data = data;
		this.unavailable = dataUnavailable;
	}

	public static DataPutRequest unavailableData(String requestId, String fieldname) {
		return new DataPutRequest(requestId, fieldname, Optional.empty(), false);
	}


	public String getFieldname() {
		return fieldname;
	}

	public SEDEObject getData() {
		return data.get();
	}

	public boolean isUnavailable() {
		return unavailable;
	}

	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("fieldname", getFieldname());
		jsonObject.put("unavailable", isUnavailable());
		if(isUnavailable()){
			jsonObject.put("data", null);
		}else{
			jsonObject.put("data", getData().toJson());
		}
		return jsonObject;
	}

	public void fromJson(Map<String, Object> data) {
		super.fromJson(data);
		fieldname = Objects.requireNonNull((String)data.get("fieldname"));
		unavailable = (boolean) data.get("unavailable");
		if(isUnavailable()){
			this.data = Optional.empty();
		} else {
			Map<String, Object> sedeJsonData = (Map<String, Object>) data.get("data");
			SEDEObject sedeObject =  SEDEObject.constructFromJson(sedeJsonData);
			this.data = Optional.of(sedeObject);
		}
	}

}
