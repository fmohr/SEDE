package de.upb.sede.exec;

import java.util.Objects;
import java.util.function.Function;

import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;

public class Execution{
	ExecutionGraph graph;
	ExecutionEnvironment environment;
	String requestID;
	DataManager dataManager;
	Function<String,BasicClientRequest> clientRequestSupplier = (url)-> new HTTPClientRequest(url);
	
	public Execution(ExecutionGraph graph, String requestID) {
		Objects.requireNonNull(graph);
		Objects.requireNonNull(requestID);
		this.graph = graph;
		this.requestID = requestID;
	}
	
	public ExecutionEnvironment getExecutionEnvironment() {
		return environment;
	}

	public BasicClientRequest getClientRequest(String url) {
		return clientRequestSupplier.apply(url);
	}
}
