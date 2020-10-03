package ai.services.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.json.simple.JSONObject;

import ai.services.util.FileUtil;
import ai.services.util.JsonSerializable;

public class ExecutorConfiguration implements JsonSerializable {
	private static final String UNDEFINED_SERVICE_STORE_LOC = "instances";

	private String serviceStoreLocation = UNDEFINED_SERVICE_STORE_LOC;
	private String executorId;
	private int threadNumber = 4;
	private List<String> capabilities = new ArrayList<>();
	private List<String> services = new ArrayList<>();
	private List<String> gateways = new ArrayList<>();

	private ExecutorConfiguration() {
        selectRandomID();
	}

	public static ExecutorConfiguration parseJSONFromFile(String configPath) {
		String jsonString = FileUtil.readFileAsString(configPath);
		return parseJSON(jsonString);
	}

	public static ExecutorConfiguration parseJSON(String jsonString) {
		Objects.requireNonNull(jsonString);
		ExecutorConfiguration newConfigInstance = new ExecutorConfiguration();
		newConfigInstance.fromJsonString(jsonString);
		return newConfigInstance;
	}

	private void selectRandomID() {
        executorId = UUID.randomUUID().toString();
    }

	public static ExecutorConfiguration getDefaultInstance() {
		return new ExecutorConfiguration();
	}

	public String getServiceStoreLocation() {
		return serviceStoreLocation;
	}

	public String getExecutorId() {
		return executorId;
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

	public List<String> getGateways() {
		return gateways;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject objectAsJsonObj = new JSONObject();
		objectAsJsonObj.put("capabilities", capabilities);
		objectAsJsonObj.put("services", services);
		objectAsJsonObj.put("executorId", executorId);
		objectAsJsonObj.put("threadNumber", threadNumber);
		objectAsJsonObj.put("serviceStoreLocation", serviceStoreLocation);
		objectAsJsonObj.put("gateways", gateways);
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
		} else {
            selectRandomID();
        }
		if (jsonObj.containsKey("threadNumber")) {
			threadNumber =  ((Number) jsonObj.get("threadNumber")).intValue();
		}
		if (jsonObj.containsKey("serviceStoreLocation")) {
			serviceStoreLocation = (String) jsonObj.get("serviceStoreLocation");
		}
		if(jsonObj.containsKey("gateways")) {
			gateways = new ArrayList<>();
			;
			/*
			 * Remove "/" at end of every address if necessary, before adding it to the list:
			 */
			Objects.requireNonNull((List<String>) jsonObj.get("gateways")).stream()
					.map(address -> address.endsWith("/")? address.substring(0, address.length()-1):address)
					.forEach(gateways::add);
		}
	}
}
