package de.upb.sede.exec;

import java.util.Objects;
import java.util.function.Supplier;

import de.upb.sede.webinterfaces.client.BasicClientRequest;

public class Execution{
	ExecutionGraph graph;
	ExecutionEnvironment environment;
	String requestID;
	DataManager dataManager;
	Supplier<BasicClientRequest> sendDataRequestSupplier;
	
	public Execution(ExecutionGraph graph, String requestID) {
		Objects.requireNonNull(graph);
		Objects.requireNonNull(requestID);
		this.graph = graph;
		this.requestID = requestID;
	}
	
	public ExecutionEnvironment getExecutionEnvironment() {
		return environment;
	}
}
