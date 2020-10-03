package ai.services.requests;

import ai.services.core.SEDEObject;
import ai.services.core.SemanticStreamer;
import ai.services.core.ServiceInstanceHandle;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.Optional;

public class Result extends Request{

	private Optional<String> fieldname = Optional.empty();
	private Optional<SEDEObject> resultData = Optional.empty();

	public Result(String requestId, String fieldname, SEDEObject resultData) {
		this(requestId, Optional.of(fieldname), Optional.of(resultData));
	}

	private Result(String requestId, Optional<String> fieldname, Optional<SEDEObject> resultData) {
		super(requestId);
		this.fieldname = fieldname;
		this.resultData = resultData;
	}

	public Result() {
	}

	public static Result failed(String requestId, String fieldname){
		return new Result(requestId, Optional.of(fieldname), Optional.empty());
	}

	public String getFieldname() {
		return fieldname.get();
	}

	public SEDEObject getResultData() {
		return resultData.get();
	}

	public ServiceInstanceHandle getServiceInstanceHandle(){
		return resultData.get().getServiceHandle();
	}

	/**
	 * Only use for testing.
	 */
	public SEDEObject castResultData(String expectedType, Class caster) {
		if(expectedType.equals(getResultData().getType())) {
			/*
				Type already matches:
			 */
			return getResultData();
		} else if(caster == null){
			throw new RuntimeException("Caster is null but data doesn;t match expected type: " + getResultData().toString());
		} else {
			return SemanticStreamer.readObjectFrom(getResultData(),
					caster.getName(), getResultData().getType(), expectedType);
		}
	}

	private void setFieldname(String fieldname) {
		this.fieldname = Optional.of(fieldname);
	}

	private void setResultData(SEDEObject resultData) {
		this.resultData = Optional.of(resultData);
	}

	public boolean hasFailed() {
		assert fieldname.isPresent();
		return !resultData.isPresent();
	}

	@Override
	public JSONObject toJson() {
		JSONObject jsonObject = super.toJson();
		jsonObject.put("fieldname", getFieldname());
		if(!hasFailed()) {
			jsonObject.put("result-data", getResultData().toJson());
		}
		return jsonObject;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		super.fromJson(data);
		setFieldname((String) data.get("fieldname"));
		if(data.containsKey("result-data")) {
			SEDEObject resultData = SEDEObject.constructFromJson((Map<String, Object>) data.get("result-data"));
			setResultData(resultData);
		} else {
			resultData = Optional.empty();
		}
	}
}
