package de.upb.sede.exec;

import de.upb.sede.gateway.Gateway;
import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AddressRetriever {

	private final static Logger logger = LogManager.getLogger();

	public static void enablePlugin(ExecutorHttpServer executor) {
		Optional<String> myAddress = retrieveAddressFromEnvironmentVar();
		if(myAddress.isPresent()){
			logger.info("Changing the address of this executor to {}", myAddress);
			executor.setHostAddress(myAddress.get());
			executor.registerToEveryGateway();
		} else {
			logger.warn("`EXECUTOR_ADDRESS` is not defined in environment variable.");
		}
	}


	private static Optional<String> retrieveAddressFromEnvironmentVar() {
		return Optional.ofNullable(System.getenv("EXECUTOR_ADDRESS"));
	}

	private static Optional<String> retrieveAddressFromGateway(ExecutorHttpServer executor) {
		List<String> gateways = executor.getBasisExecutor().getExecutorConfiguration().getGateways();
		/**
		 * first lookup the address:
		 */
		if(gateways.isEmpty()){
			return Optional.empty(); // cant lookup the address if there are no gateways.
		}
		logger.info("Asking gatways for the address of this executor in the network.");
		List<String> myAddresses = gateways.stream().map(
				gatewayAddress ->
				{
					try{
						HttpURLConnectionClientRequest addressLookup =
								new HttpURLConnectionClientRequest(gatewayAddress+ "/what-is-my-addr");
						return addressLookup.send("");
					} catch(Exception ex) {
						logger.error("Couldn't ask gateway '{}' for the address of this executor. ", ex);
						return null;
					}
				})
				.filter(address -> address!=null && !address.startsWith("127.0.0.1")) // remove every same-host address.
				.collect(Collectors.toList());
		if(myAddresses.isEmpty()) {
			logger.warn("Couldn't determine the address of this executor by asking gateways.");
			return Optional.empty();
		}
		else {
			String myAddress = myAddresses.get(0) +":"+ executor.getServerTCPPort(); // TODO not sure how to select my address
			return Optional.of(myAddress);
		}
	}
}
