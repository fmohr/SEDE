package de.upb.sede.exec;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.gateway.GatewayCommands;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.Command;
import de.upb.sede.webinterfaces.server.CommandTree;
import de.upb.sede.webinterfaces.server.ServerCommandListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.upb.sede.webinterfaces.server.CommandTree.*;

public class HttpExecutorCommands {

	private final static Logger logger = LogManager.getLogger();

	private final ExecutorHttpServer executor;

	public static HttpExecutorCommands enablePlugin(ExecutorHttpServer executor, ServerCommandListeners scl){
		return new HttpExecutorCommands(executor, scl);
	}

	public HttpExecutorCommands(ExecutorHttpServer executor, ServerCommandListeners scl){
		this.executor = executor;
		CommandTree registration = new CommandTree(
				node(new Command.Strings(false, "register"),
						node(new Command.Strings("to"),
								node(Command.rest()
										.addExe(t->registerToGateway(lastMatch(t))))),
						node(new Command.Strings("again")
								.addExe(t->reregister()))

				)
		);
		CommandTree changeAddress = new CommandTree(node(new Command.Strings("change-address"),
															node(Command.rest().addExe(t-> changeAddress(lastMatch(t))))));

		scl.addCommandHandle(registration);
		scl.addCommandHandle(changeAddress);
	}



	public String registerToGateway(String gatewayAddress) {
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

	public String changeAddress(String newAddress) {
		logger.debug("The host address is set to be changed. Given inputs: {}", newAddress);
		/*
			First change the address in the contact info map:
		 */
		executor.setHostAddress(newAddress);
		executor.registerToEveryGateway();
		return "Changed the address to '" + newAddress + "'.";
	}

	public String reregister() {
		String listOfGateways = executor.getBasisExecutor().getExecutorConfiguration().getGateways().toString();
		logger.debug("Reregistering to every gateway: {}", listOfGateways);
		executor.registerToEveryGateway();
		return "Registered to every gateway in this list:\n\t" + listOfGateways;
	}


}
