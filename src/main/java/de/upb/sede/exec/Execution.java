package de.upb.sede.exec;

import java.util.Objects;
import java.util.function.Function;

import de.upb.sede.core.ServiceInstanceHandle;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;

public class Execution {
	ExecutionGraph graph;
	ExecutionEnvironment environment;
	String requestID;
	Function<String, BasicClientRequest> clientRequestSupplier = (url) -> new HTTPClientRequest(url);
	Function<Object,ServiceInstanceHandle> serviceInstanceHandleSupplier;


	
	public Execution(String requestID) {
		Objects.requireNonNull(requestID);
		this.requestID = requestID;
	}
	
	public ExecutionEnvironment getExecutionEnvironment() {
		return environment;
	}

	public BasicClientRequest getClientRequest(String url) {
		return clientRequestSupplier.apply(url);
	}

	public ExecutionGraph getGraph() {
		return graph;
	}

	public void setGraph(ExecutionGraph graph) {
		this.graph = graph;
	}

	public ExecutionEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(ExecutionEnvironment environment) {
		this.environment = environment;
	}

	public String getRequestID() {
		return requestID;
	}

	public Function<String, BasicClientRequest> getClientRequestSupplier() {
		return clientRequestSupplier;
	}

	public Function<Object,ServiceInstanceHandle> getServiceInstanceHandleSupplier(){
		return serviceInstanceHandleSupplier;
	}
	
	
}
interface ExecutionObserver {

	public boolean conditionSatisfies(Execution exec);

	public void update(Execution exec);
}
