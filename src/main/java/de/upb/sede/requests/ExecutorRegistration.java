package de.upb.sede.requests;

import java.util.List;

public class ExecutorRegistration {
	private final String myHost;

	private final List<String> capabilities;

	private final List<String> supportedServices;

	public ExecutorRegistration(String myHost, List<String> capabilities, List<String> supportedServices) {
		super();
		this.myHost = myHost;
		this.capabilities = capabilities;
		this.supportedServices = supportedServices;
	}

	/**
	 * @return the myHost
	 */
	public String getHost() {
		return myHost;
	}

	/**
	 * @return the capabilities
	 */
	public List<String> getCapabilities() {
		return capabilities;
	}

	/**
	 * @return the supportedServices
	 */
	public List<String> getSupportedServices() {
		return supportedServices;
	}

}