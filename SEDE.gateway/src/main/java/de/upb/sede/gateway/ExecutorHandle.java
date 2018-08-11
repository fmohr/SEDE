package de.upb.sede.gateway;

import java.util.Map;
import java.util.Objects;

public class ExecutorHandle {

	private final Map<String, Object>  contactInfo;
	private final String executorId;
	private final ExecutorCapabilities capabilities;

	public ExecutorHandle(String executorId, Map<String, Object>  contactInfo, String... executorCapabilities) {
		this.executorId = Objects.requireNonNull(executorId);
		this.contactInfo = Objects.requireNonNull(contactInfo);
		this.capabilities = new ExecutorCapabilities(executorCapabilities);
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
