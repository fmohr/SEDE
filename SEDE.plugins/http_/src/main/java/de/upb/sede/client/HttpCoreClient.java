package de.upb.sede.client;

import de.upb.sede.exec.Executor;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.webinterfaces.client.BasicClientRequest;
import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class offers a few static methods that create a CoreClient which can reach a gateway over http.
 */
public final class HttpCoreClient {
	private final static Logger logger = LogManager.getLogger();

	public static CoreClient createWithGiven(Executor executor, String gatewayAddress, int gatewayPort) {
		return new CoreClient(executor, rr -> resolveRequestOverHttp(rr, gatewayAddress, gatewayPort));
	}

	public static CoreClient createNew(final ExecutorConfiguration executorConfig, final String clientAddress,
									   final int clientPort, final String gatewayAddress, final int gatewayPort) {
		return new CoreClient(simpleHttpClientExecutor(executorConfig, clientAddress, clientPort),
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
					" Returned exception: "+parseException.getMessage());
		}
		return gatewayResolution;
	}

	public static Executor simpleHttpClientExecutor(ExecutorConfiguration executorConfig, String clientAddress,
													int clientPort) {
		Executor executor = new Executor(executorConfig);
		ExecutorHttpServer pluging = new ExecutorHttpServer(executor, clientAddress, clientPort);
		return executor;
	}


}
