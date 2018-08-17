package de.upb.sede.exec;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.ServerCommandListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ExecutorServerStarter {
	private final static Logger logger = LogManager.getLogger();
	private final ServerCommandListener commandListener;
	private final ExecutorHttpServer executor;


	private ExecutorServerStarter(String configPath, String serverHostAddress, int serverPort) throws InterruptedException {
		ExecutorConfiguration executorConfiguration = ExecutorConfiguration.parseJSONFromFile(configPath);

		executor = new ExecutorHttpServer(executorConfiguration, serverHostAddress, serverPort);



		commandListener = new ServerCommandListener(executor);
		commandListener.addCommandHandle("register-to", new RegisterToGateway());
		commandListener.addCommandHandle("list-services", new ListServices());
		commandListener.addCommandHandle("change-address", new ChangeAddress());
		commandListener.addCommandHandle("reregister", new Reregister());
		commandListener.listenEndlessly();
	}

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Arrays.toString(args));
		if(args == null || args.length != 3) {
			throw new RuntimeException("Need 3 input arguments.");
		}
		String configPath = args[0];
		String serverHostAddress = args[1];
		int serverPort = Integer.parseInt(args[2]);

		logger.info("Starting executor with: \nconfig:{}\nip address:{}\nport:{}",
				configPath, serverHostAddress, serverPort);

		new ExecutorServerStarter(configPath, serverHostAddress, serverPort);
	}

	class RegisterToGateway implements Function<List<String>, String> {

		@Override
		public String apply(List<String> inputs) {
			if(inputs == null || inputs.isEmpty()) {
				return "Provide the address of the gateway as the first parameter..";
			}
			String gatewayAddress = inputs.get(0);
			logger.info("Received command to register to gateway with address: {}.", gatewayAddress);

			try{
				/*
					Add gateway to the list of gateways:
				 */
				executor.getBasisExecutor().getExecutorConfiguration().getGateways().add(gatewayAddress);
				executor.registerToGateway(gatewayAddress);
				return "Successfully registered to " + gatewayAddress;
			} catch(Exception ex) {
				return "Registration to " + gatewayAddress + " failed.\nError: " + Streams.ErrToString(ex);
			}
		}
	}


	class ListServices implements Function<List<String>, String> {
		@Override
		public String apply(List<String> inputs) {
			ExecutorConfiguration configuration = executor.getBasisExecutor().getExecutorConfiguration();
			String knownClasses = configuration.getSupportedServices().stream().sorted()
					.collect(Collectors.joining("\n"));
			return knownClasses;
		}
	}

	class ChangeAddress implements Function<List<String>, String> {
		@Override
		public String apply(List<String> inputs) {
			logger.debug("The host address is set to be changed. Given inputs: {}", inputs);
			String newAddress;
			if(inputs.size() == 0) {
				return "Input list is empty. Provide the new address as an input.";
			} else if(inputs.size() == 1) {
				newAddress = inputs.get(0);
			} else if(inputs.size() == 2) {
				newAddress = inputs.get(0) + ":" + inputs.get(1);
			} else {
				return "Error: Input list for address change contains more than 2 elements: " + inputs.toString();
			}
			/*
			 	First change the address in the contact info map:
			 */
			executor.setHostAddress(newAddress);
			executor.registerToEveryGateway();
			return "Changed the address to '" + newAddress + "'.";
		}
	}

	class Reregister implements Function<List<String>, String> {
		@Override
		public String apply(List<String> inputs) {
			String listOfGateways = executor.getBasisExecutor().getExecutorConfiguration().getGateways().toString();
			logger.debug("Reregistering to every gateway: {}", listOfGateways);
			executor.registerToEveryGateway();
			return "Registered to every gateway in this list:\n\t" + listOfGateways;
		}
	}
}
