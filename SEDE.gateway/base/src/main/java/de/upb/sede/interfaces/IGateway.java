package de.upb.sede.interfaces;

import de.upb.sede.beta.IExecutorRegistration;

/**
 * Interface of Gateway logic.
 *
 * Offers interface to register executors and resolving composition to graph.
 *
 */
public interface IGateway extends ICCService, IChoreographyService {

    /**
	 * Registers the executor who sent the given ExecutorRegistration.
	 * Registered executor will be assigned to task in the resolution graph.
	 * If an executor is already registered under the same host as the executor host in the given registration,
	 * nothing will happen. TODO should raise an error?
	 *
	 * @param registry  sent by the executor that wants to be registered.
	 * @return true, if registration was successful.
	 */
	public boolean register(IExecutorRegistration registry);
}
