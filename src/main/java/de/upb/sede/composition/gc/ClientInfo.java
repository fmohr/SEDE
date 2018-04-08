package de.upb.sede.composition.gc;

import java.util.Objects;

public class ClientInfo {
	private final String clientHostAddress;
	
	public ClientInfo(String address) {
		this.clientHostAddress = Objects.requireNonNull(address);
	}
	
	public String getClientHostAddress() {
		return clientHostAddress;
	}

	public ExecutorHandle getClientExecutor() {
		return null;
	}
}
