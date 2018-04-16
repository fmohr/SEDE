package de.upb.sede.exec;

import java.util.Objects;

public class Execution{
	ExecutionGraph graph;
	ExecutionEnvironment environment;
	String requestID;
	DataManager dataManager;
	
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
