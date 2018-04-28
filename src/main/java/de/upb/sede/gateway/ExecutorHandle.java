package de.upb.sede.gateway;

import java.util.Objects;

public class ExecutorHandle {
	private final static String UNKNOWN_EXEC_ADDRESS = "UNKNOWN";
	private final static ExecutorHandle UNKNOWN_EXEC = new ExecutorHandle(UNKNOWN_EXEC_ADDRESS);

	private final String hostAddress;
	private final ExecutorCapabilities capabilities;

	public ExecutorHandle(String hostAddress, String... executorCapabilities) {
		this.hostAddress = Objects.requireNonNull(hostAddress);
		this.capabilities = new ExecutorCapabilities(executorCapabilities);
	}

	public static ExecutorHandle BASIC_JAVA_GATEWAY(String hostAddress) {
		return new ExecutorHandle(hostAddress, ExecutorCapabilities.javalibs);
	}

	public static ExecutorHandle UNKNOWN_EXECUTOR() {
		return UNKNOWN_EXEC;
	}

	public ExecutorCapabilities getExecutionerCapabilities() {
		return capabilities;
	}

	public String getHostAddress() {
		return hostAddress;
	}

	public boolean equalsHostAddress(String execHostAddress) {
		return hostAddress.equals(execHostAddress);
	}

	public boolean unknownExecutor() {
		return getHostAddress().equals(UNKNOWN_EXEC_ADDRESS);
	}

}
