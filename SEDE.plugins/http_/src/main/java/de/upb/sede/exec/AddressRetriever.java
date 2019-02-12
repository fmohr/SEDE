package de.upb.sede.exec;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.webinterfaces.client.HttpURLConnectionClientRequest;

public class AddressRetriever {

	private final static Logger logger = LoggerFactory.getLogger(AddressRetriever.class);

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

}
