package de.upb.sede.exec;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.PluginUtil;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.util.server.TerminalCommandListener;
import de.upb.sede.webinterfaces.server.ImServerCommandListener;
import de.upb.sede.webinterfaces.server.ServerCommandListeners;
import de.upb.sede.webinterfaces.server.StdShellCommands;

public class ExecutorServerStarter {

	private final static Logger logger = LoggerFactory.getLogger(ExecutorServerStarter.class);

	public static void main(String[] args) {
		System.out.println(Arrays.toString(args));
		if(args == null || args.length != 3) {
			throw new RuntimeException("Need 3 input arguments.");
		}
		String configPath = args[0];
		String serverHostAddress = args[1];
		int serverPort = Integer.parseInt(args[2]);

		logger.info("Starting executor with: \nconfig:{}\nip address:{}\nport:{}",
				configPath, serverHostAddress, serverPort);

		ExecutorConfiguration executorConfiguration = ExecutorConfiguration.parseJSONFromFile(configPath);

		ExecutorHttpServer httpExecutor = new ExecutorHttpServer(executorConfiguration, serverHostAddress, serverPort);
		Executor executor = httpExecutor.getBasisExecutor();

		/*
		 * Enable plugins:
		 */
		AddressRetriever.enablePlugin(httpExecutor);
		ProxySetup.enablePlugin(httpExecutor, httpExecutor.getBasisExecutor());

		ImServerCommandListener httpListener = new ImServerCommandListener(httpExecutor, httpExecutor.getBasisExecutor().getExecutorConfiguration().getExecutorId());
		TerminalCommandListener terminalListener = new TerminalCommandListener();

		ServerCommandListeners scl = new ServerCommandListeners();

		scl.addListener(httpListener);
		scl.addListener(terminalListener);


		ExecutorCommands.enablePlugin(executor, scl);
		HttpExecutorCommands.enablePlugin(httpExecutor, httpExecutor.getBasisExecutor(), scl);
		StdShellCommands.enablePlugin(scl);

		/*
		 * Terminals:
		 */
		PluginUtil.enablePlugin_SystemTerminal(terminalListener);
	}

}
