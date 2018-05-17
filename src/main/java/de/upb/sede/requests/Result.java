package de.upb.sede.requests;

import de.upb.sede.core.SEDEObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Result extends  Request{

	private Optional<String> fieldname = Optional.empty();
	private Optional<SEDEObject> resultData = Optional.empty();

	public Result(String requestId, String fieldname, SEDEObject resultData) {
		super(requestId);
		setFieldname(fieldname);
		setResultData(resultData);
	}


	public Result() {
	}

	public String getFieldname() {
		return fieldname.get();
	}

	public SEDEObject getResultData() {
		return resultData.get();
	}

	private void setFieldname(String fieldname) {
		this.fieldname = Optional.of(fieldname);
	}

	private void setResultData(SEDEObject resultData) {
		this.resultData = Optional.of(resultData);
	}

	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("fieldname", getFieldname());
		jsonObject.put("result-data", getResultData().toJson());
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		super.fromJson(data);
		setFieldname((String) data.get("fieldname"));
		SEDEObject resultData = SEDEObject.constructFromJson((Map<String, Object>) data.get("result-data"));
		setResultData(resultData);
	}
}
