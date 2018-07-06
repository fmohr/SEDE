package de.upb.sede.interfaces;

import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;

/**
 * Interface of Gateway logic. 
 * 
 * Offers interface to register executors and resolving composition to graph.
 *
 */
public interface IGateway {
	
	/**
	 * Accepts a ResolveRequest and parses the included fmcomposition and constructs the graph for the CoreClient to execute.
	 * Together these series of actions are called 'resolve' and the resulting graph is called 'resolution'.
	 * 
	 * @param resolveRequest request object from the client.
	 * @return resolution of the composition.
	 */
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