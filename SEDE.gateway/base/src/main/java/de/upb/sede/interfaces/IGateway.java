package de.upb.sede.interfaces;

import de.upb.sede.composition.ICCRequest;
import de.upb.sede.composition.ICompositionCompilation;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;

/**
 * Interface of Gateway logic.
 *
 * Offers interface to register executors and resolving composition to graph.
 *
 */
public interface IGateway extends ICCService {

    /**
     * This is the old way of executing compositions.
     * It is now being replaced by compile composition.
     */
	@Deprecated
    public GatewayResolution resolve(ResolveRequest resolveRequest);

    /**
	 * Registers the executor who sent the given ExecutorRegistration.
	 * Registered executor will be assigned to task in the resolution graph.
	 * If an executor is already registered under the same host as the executor host in the given registration,
	 * nothing will happen. TODO should raise an error?
	 *
	 * @param registry  sent by the executor that wants to be registered.
	 * @return true, if registration was successful.
	 */
	public boolean register(ExecutorRegistration registry);
}
