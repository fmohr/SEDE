package de.upb.sede.exec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.upb.sede.util.FileUtil;
import de.upb.sede.util.JsonSerializable;

public class ExecutorConfiguration implements JsonSerializable {
	private static final String UNDEFINED_SERVICE_STORE_LOC = "No location defined to store services in.";
	private static final String DEFAULT_GATEWAY_ID = "Default ID for gateway";
	
	private String serviceStoreLocation = UNDEFINED_SERVICE_STORE_LOC;
	private String executorId = UUID.randomUUID().toString();
	private String gateWayId = DEFAULT_GATEWAY_ID;
	private int threadNumber = 1;
	private List<String> capabilities = new ArrayList<>();
	private List<String> services = new ArrayList<>();

	private ExecutorConfiguration() {
	}

	@SuppressWarnings("unchecked")
	public static ExecutorConfiguration parseJSON(String configPath) {
		ExecutorConfiguration newConfigInstance = new ExecutorConfiguration();
		if (!Objects.isNull(configPath)) {
			JSONParser jsonParser = new JSONParser();
			String fileContent = FileUtil.readFileAsString(configPath);
			JSONObject jsonConf;
			try {
				jsonConf = (JSONObject) jsonParser.parse(fileContent);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
			newConfigInstance.fromJson(jsonConf);
		}
		return newConfigInstance;
	}

	public static ExecutorConfiguration getDefaultInstance() {
		return null; // TODO
	}

	public String getServiceStoreLocation() {
		return serviceStoreLocation;
	}

	public String getExecutorId() {
		return executorId;
	}

	//TODO Get rid of this method. Only here to make tests runnable.
	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}

	public String getGateWayId() {
		return gateWayId;
	}

	public int getThreadNumber() {
		return threadNumber;
	}

	public List<String> getExecutorCapabilities() {
		return capabilities;
	}

	public List<String> getSupportedServices() {
		return services;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject objectAsJsonObj = new JSONObject();
		if (!Objects.isNull(capabilities) && !capabilities.isEmpty()) {
			objectAsJsonObj.put("capabilities", capabilities);
		}
		if (!Objects.isNull(services) && !services.isEmpty()) {
			objectAsJsonObj.put("services", services);
		}
		if (!Objects.isNull(executorId)) {
			objectAsJsonObj.put("executorId", executorId);
		}
		if (!Objects.isNull(gateWayId)) {
			objectAsJsonObj.put("gateWayId", gateWayId);
		}
		if (threadNumber == 0) {
			objectAsJsonObj.put("threadNumber", threadNumber);
		}
		if (!Objects.isNull(serviceStoreLocation)) {
			objectAsJsonObj.put("serviceStoreLocation", serviceStoreLocation);
		}
		return objectAsJsonObj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void fromJson(Map<String, Object> jsonObj) {
		if (jsonObj.containsKey("capabilities")) {
			capabilities = (List<String>) jsonObj.get("capabilities");
		}
		if (jsonObj.containsKey("services")) {
			services = (List<String>) jsonObj.get("services");
		}
		if (jsonObj.containsKey("executorId")) {
			executorId = (String) jsonObj.get("executorId");
		}
		if (jsonObj.containsKey("gateWayId")) {
			gateWayId = (String) jsonObj.get("gateWayId");
		}
		if (jsonObj.containsKey("threadNumber")) {
			threadNumber = (Integer) jsonObj.get("threadNumber");
		}
		if (jsonObj.containsKey("serviceStoreLocation")) {
			serviceStoreLocation = (String) jsonObj.get("serviceStoreLocation");
		}
	}
}
