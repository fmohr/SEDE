package de.upb.sede.client;

import de.upb.sede.core.CoreClient;
import de.upb.sede.exec.Executor;
import de.upb.sede.exec.ExecutorConfiguration;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;

import java.util.function.Function;

public class CoreClientHttpServer extends CoreClient {

	public CoreClientHttpServer(Executor executor, String gatewayAddress, int gatewayPort) {
		super(executor, rr -> resolveRequestOverHttp(rr, gatewayAddress, gatewayPort));
	}

	public CoreClientHttpServer(final String clientAddress, final int clientPort, final String gatewayAddress, final int gatewayPort) {
		super(simpleClientExecutor(clientAddress, clientPort), rr -> resolveRequestOverHttp(rr, gatewayAddress, gatewayPort));
	}


	public static GatewayResolution resolveRequestOverHttp(ResolveRequest rr, String gatewayAddress, int gatewayPort) {
		BasicClientRequest requestToGateway = new HTTPClientRequest(gatewayAddress, gatewayPort, "resolve");
		String resolutionJsonString = requestToGateway.send(rr.toJsonString());
		GatewayResolution gatewayResolution = new GatewayResolution();
		gatewayResolution.fromJsonString(resolutionJsonString);
		return gatewayResolution;
	}

	public static Executor simpleClientExecutor(String clientAddress, int clientPort) {
		ExecutorConfiguration executorConfiguration = ExecutorConfiguration.parseJSON(null);
		Executor executor = new ExecutorHttpServer(executorConfiguration, clientAddress, clientPort);
		return executor;
	}

	public ExecutorHttpServer getClientExecutor(){
		return (ExecutorHttpServer) super.getClientExecutor();
	}

	public static void main(String[] args) {
		String gatewayHost = "localhost:6060";
		String clientExecHost = "client server";
	}

}
