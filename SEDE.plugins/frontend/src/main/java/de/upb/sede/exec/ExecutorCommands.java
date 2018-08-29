package de.upb.sede.exec;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.gateway.GatewayCommands;
import de.upb.sede.webinterfaces.server.Command;
import de.upb.sede.webinterfaces.server.CommandTree;
import de.upb.sede.webinterfaces.server.ServerCommandListeners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.upb.sede.webinterfaces.server.CommandTree.node;

public class ExecutorCommands {
	private final static Logger logger = LogManager.getLogger();
	private final Executor executor;

	public static ExecutorCommands enablePlugin(Executor executor, ServerCommandListeners scl){
		return new ExecutorCommands(executor, scl);
	}

	public ExecutorCommands(Executor executor, ServerCommandListeners scl){
		this.executor = executor;
		CommandTree services = new CommandTree(
				node(new Command.Strings(false, "services"),
						node(new Command.Strings("ls").addExe(t->this.listServices()))
				)
		);
		CommandTree heartbeat = new CommandTree(node(
				new Command.Strings("heartbeat").addExe(t-> heartbeat())));

		scl.addCommandHandle(services);
		scl.addCommandHandle(heartbeat);
	}



	public String listServices() {
		ExecutorConfiguration configuration = executor.getExecutorConfiguration();
		String supportedServices = configuration.getSupportedServices().stream().sorted()
				.collect(Collectors.joining("\n"));
		return supportedServices;
	}


	public String heartbeat() {
		logger.debug("Someone querried if this executor is still alive.");
		return "true";
	}
}
