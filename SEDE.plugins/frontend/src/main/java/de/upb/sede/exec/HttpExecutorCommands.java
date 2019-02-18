package de.upb.sede.exec;

import static de.upb.sede.webinterfaces.server.CommandTree.lastMatch;
import static de.upb.sede.webinterfaces.server.CommandTree.node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.Command;
import de.upb.sede.webinterfaces.server.CommandTree;
import de.upb.sede.webinterfaces.server.ServerCommandListeners;

public class HttpExecutorCommands {

	private final static Logger logger = LoggerFactory.getLogger(HttpExecutorCommands.class);

	private final HttpExecutor executor;
	private final Executor basis;

	public static HttpExecutorCommands enablePlugin(HttpExecutor executor, Executor basis, ServerCommandListeners scl){
		return new HttpExecutorCommands(executor, basis, scl);
	}

	public HttpExecutorCommands(HttpExecutor executor, Executor basis, ServerCommandListeners scl){
		this.executor = executor;
		this.basis = basis;
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
			basis.getExecutorConfiguration().getGateways().add(gatewayAddress);
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
