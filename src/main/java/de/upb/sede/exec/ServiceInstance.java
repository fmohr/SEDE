package de.upb.sede.exec;

import de.upb.sede.core.ServiceInstanceHandle;

import java.util.Optional;

public class ServiceInstance extends ServiceInstanceHandle{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3654980069216543011L;
	private final Object serviceInstance;

	public ServiceInstance(final String host, final String classpath, final String id, Object serviceInstance) {
		super(host, classpath, id);
		this.serviceInstance = serviceInstance;
	}

	public Optional<Object> getServiceInstance() {
		return Optional.of(serviceInstance);
	}

}
