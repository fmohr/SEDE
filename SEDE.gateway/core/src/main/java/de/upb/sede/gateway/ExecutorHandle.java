package de.upb.sede.gateway;

import de.upb.sede.requests.ExecutorRegistration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExecutorHandle {

	private final Map<String, Object>  contactInfo;
	private final String executorId;
	private final ExecutorCapabilities capabilities;

	public ExecutorHandle(String executorId, Map<String, Object>  contactInfo, String... executorCapabilities) {
		this.executorId = Objects.requireNonNull(executorId);
		this.contactInfo = Objects.requireNonNull(contactInfo);
		this.capabilities = new ExecutorCapabilities(executorCapabilities);
	}

	public static ExecutorHandle fromRegistration(ExecutorRegistration registration) {
        ExecutorHandle execHandle = new ExecutorHandle(registration.getId(),
            registration.getContactInfo(),
            registration.getCapabilities().toArray(new String[0]));
        execHandle.getExecutionerCapabilities().addAllServiceClasses(registration.getSupportedServices().toArray(new String[0]));
        return execHandle;
    }

	public ExecutorCapabilities getExecutionerCapabilities() {
		return capabilities;
	}


	public boolean equalsId(String id) {
		return getExecutorId().equals(id);
	}

	public String getExecutorId(){
		return  executorId;
	}

	public Map<String, Object> getContactInfo(){
		return contactInfo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExecutorHandle that = (ExecutorHandle) o;

		return getExecutorId().equals(that.getExecutorId());
	}

	@Override
	public int hashCode() {
		return getExecutorId().hashCode();
	}

}
