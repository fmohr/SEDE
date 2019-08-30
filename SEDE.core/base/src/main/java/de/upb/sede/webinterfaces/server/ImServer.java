package de.upb.sede.webinterfaces.server;

import java.util.function.Supplier;

public interface ImServer {

	public void shutdown();

	public void addHandle(String context, Supplier<HTTPServerResponse> serverResponder);
}
