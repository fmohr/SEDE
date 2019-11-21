package de.upb.sede.util;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ExecutorConfigurationCreator {

    public static ExecutorConfigurationCreator newConfigFile() {
		return new ExecutorConfigurationCreator();
	}

	private String serviceStoreLocation;
	private String executorId;
    private String groupId;
	private List<String> gateways = new ArrayList<>();
	private Integer threadNumber;
	private List<String> capabilities;
	private List<String> services;

	public ExecutorConfigurationCreator withServiceStoreLocation(String serviceStoreLocation) {
		this.serviceStoreLocation = serviceStoreLocation;
		return this;
	}

	public ExecutorConfigurationCreator withExecutorId(String executorId) {
		this.executorId = executorId;
		return this;
	}

	public ExecutorConfigurationCreator withGroupId(String groupId) {
	    this.groupId = groupId;
	    return this;
    }

	public ExecutorConfigurationCreator withGateway(String gatewayId) {
		gateways.add(Objects.requireNonNull(gatewayId));
		return this;
	}

	public ExecutorConfigurationCreator withThreadNumberId(Integer threadNumber) {
		this.threadNumber = threadNumber;
		return this;
	}

	public ExecutorConfigurationCreator withCapabilities(String... capabilities) {
		this.capabilities = new ArrayList<>(Arrays.asList(capabilities));
		return this;
	}

	public ExecutorConfigurationCreator withCapabilities(Collection<String> capabilities) {
		List<String> result = new ArrayList<>();
		capabilities.forEach(capability -> result.add(capability));
		this.capabilities = result;
		return this;
	}

	public ExecutorConfigurationCreator withSupportedServices(String... serviceNames) {
		this.services = new ArrayList<>(Arrays.asList(serviceNames));
		return this;
	}

	public ExecutorConfigurationCreator withSupportedServices(Collection<String> serviceNames) {
		List<String> result = new ArrayList<>();
		serviceNames.forEach(name -> result.add(name));
		this.services = result;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		JSONObject obj = new JSONObject();
		if (serviceStoreLocation != null) {
			obj.put("serviceStoreLocation", serviceStoreLocation);
		}
		if (executorId != null) {
			obj.put("executorId", executorId);
		}
		if(groupId != null) {
		    obj.put("groupId", groupId);
        }

		obj.put("gateways", gateways);

		if (threadNumber != null) {
			obj.put("threadNumber", threadNumber);
		}
		if (capabilities != null) {
			JSONArray capabilitiesList = new JSONArray();
			capabilitiesList.addAll(capabilities);
			obj.put("capabilities", capabilitiesList);
		}
		if (services != null) {
			JSONArray servicesList = new JSONArray();
			servicesList.addAll(services);
			obj.put("services", servicesList);
		}
		return obj.toJSONString();
	}


}
