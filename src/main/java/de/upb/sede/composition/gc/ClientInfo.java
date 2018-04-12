package de.upb.sede.composition.gc;

import java.util.Objects;

public class ClientInfo {
	private final String clientHostAddress;
	private final ExecutorHandle clientExecutor;
	
	
	public ClientInfo(String address) {
		this.clientHostAddress = Objects.requireNonNull(address);
		this.clientExecutor = new ExecutorHandle(clientHostAddress);
	}
	
	public String getClientHostAddress() {
		return clientHostAddress;
	}

	public ExecutorHandle getClientExecutor() {
		return clientExecutor;
	}
}
