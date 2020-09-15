package ai.services.core;

import java.util.Objects;
import java.util.Optional;

public class ServiceInstance extends ServiceInstanceHandle {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final Object serviceInstance;

	public ServiceInstance(final String executorId, final String classpath, final String id, Object serviceInstance) {
		super(executorId, classpath, id);
		this.serviceInstance = Objects.requireNonNull(serviceInstance);
	}

	public ServiceInstance(final ServiceInstanceHandle serviceInstanceHandler, Object serviceInstance) {
	    this(serviceInstanceHandler.getExecutorId(), serviceInstanceHandler.getClasspath(), serviceInstanceHandler.getId(), serviceInstance);
    }

	public Optional<Object> getServiceInstance() {
		return Optional.of(serviceInstance);
	}

}
