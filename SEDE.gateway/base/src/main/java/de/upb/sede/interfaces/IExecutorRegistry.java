package de.upb.sede.interfaces;

import de.upb.sede.beta.IExecutorRegistration;

public interface IExecutorRegistry {

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
