package de.upb.sede.exec;

import de.upb.sede.webinterfaces.server.ImServer;

public interface HttpExecutor extends ImServer {

	void setHostAddress(String s);

	void registerToEveryGateway();

	String getExecutorId();

	String getHostAddress();

	void registerToGateway(String gatewayAddress);

	Executor getBasisExecutor();
}