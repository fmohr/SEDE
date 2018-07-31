package de.upb.sede.client;

import de.upb.sede.client.CoreClient;
import de.upb.sede.exec.Executor;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;

public class CoreClientHttpServer extends CoreClient {

	public CoreClientHttpServer(Executor executor, String gatewayAddress, int gatewayPort) {
		super(executor, rr -> resolveRequestOverHttp(rr, gatewayAddress, gatewayPort));
	}

	public CoreClientHttpServer(final ExecutorConfiguration executorConfig, final String clientAddress,
			final int clientPort, final String gatewayAddress, final int gatewayPort) {
		super(simpleClientExecutor(executorConfig, clientAddress, clientPort),
				rr -> resolveRequestOverHttp(rr, gatewayAddress, gatewayPort));
	}

	public static GatewayResolution resolveRequestOverHttp(ResolveRequest rr, String gatewayAddress, int gatewayPort) {
		BasicClientRequest requestToGateway = new HttpURLConnectionClientRequest(gatewayAddress, gatewayPort, "resolve");
		String resolutionJsonString = requestToGateway.send(rr.toJsonString());
		GatewayResolution gatewayResolution = new GatewayResolution();
		try {
			gatewayResolution.fromJsonString(resolutionJsonString);
		} catch(RuntimeException parseException){
			logger.error("Resoltion parse error: ", parseException);
			throw new RuntimeException("Gateway didn't resolve the composition." +
					" Returned exception:\n"+resolutionJsonString);
		}
		return gatewayResolution;
	}

	public static Executor simpleClientExecutor(ExecutorConfiguration executorConfig, String clientAddress,
			int clientPort) {
		Executor executor = new ExecutorHttpServer(executorConfig, clientAddress, clientPort);
		return executor;
	}

	public ExecutorHttpServer getClientExecutor() {
		return (ExecutorHttpServer) super.getClientExecutor();
	}
}
