package de.upb.sede.gateway;

import de.upb.sede.PluginUtil;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Terminals;
import de.upb.sede.util.WebUtil;
import de.upb.sede.util.server.TerminalCommandListener;
import de.upb.sede.webinterfaces.server.ImServerCommandListener;
import de.upb.sede.webinterfaces.server.ServerCommandListeners;
import de.upb.sede.webinterfaces.server.StdShellCommands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GatewayServerStarter {
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(GatewayServerStarter.class);


	public static void main(String[] args) throws InterruptedException {

		if(args == null || args.length ==0) {
			throw new RuntimeException("Need at least 1 input argument: port");
		}
		int serverPort = Integer.parseInt(args[0]);
		
		List<String> configsToBeLoaded = new ArrayList<>(Arrays.asList(args));
		configsToBeLoaded.remove(0); // remove the port


		logger.info("Starting gateway with: \nport:{}\nconfigurations:{}", serverPort, configsToBeLoaded);


		ClassesConfig classesConfig = new ClassesConfig();
		OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
		loadDefaultConfigs(classesConfig, typeConfig, configsToBeLoaded);

		GatewayHttpServer httpGateway = new GatewayHttpServer(serverPort, classesConfig, typeConfig);
		Gateway gateway = httpGateway.getBasis();

		/*
		 * Enable plugins:
		 */

		WhatIsMyAddressHandler.enablePlugin(httpGateway);

		ImServerCommandListener httpListener = new ImServerCommandListener(httpGateway);
		TerminalCommandListener terminalListener = new TerminalCommandListener();

		ServerCommandListeners scl = new ServerCommandListeners();

		scl.addListener(httpListener);
		scl.addListener(terminalListener);

		GatewayCommands.enablePlugin(gateway, scl);
		StdShellCommands.enablePlugin(scl);

		/*
		 * Terminals:
		 */
		PluginUtil.enablePlugin_SystemTerminal(terminalListener);
		PluginUtil.enablePlugin_TellnetServer(terminalListener);
	}


	static void loadDefaultConfigs(ClassesConfig classesConfig, OnthologicalTypeConfig typeConfig,
										   List<String> configsToBeLoaded) {
		for(String configuration : configsToBeLoaded) {
			/*
				look for json files ending with classconf.json or typeconf.json in the given list
				and try to append them to the class/type configuration:
			 */
			if(configuration.matches("(.*?)classconf\\.json$")) {
				try{
					classesConfig.appendConfigFromFiles(configuration);
					logger.info("Added classes config from: {}" , configuration);
				}
				catch(Exception ex) {
					logger.error("Problem loading class configuration from {}:\n", configuration, ex);
				}
			} else if(configuration.matches("(.*?)typeconf\\.json$")) {
				try {
					typeConfig.appendConfigFromFiles(configuration);
					logger.info("Added type config from: {}", configuration);
				} catch (Exception ex) {
					logger.error("Problem loading type configuration from {}:\n", configuration, ex);
				}
			} else{
				logger.info("Config file '{}' doesn't end with classconf.json or typeconf.json.", configuration);
				if(new File(configuration).isDirectory()) {
					/*
					 * load every class conf file  from the given folder:
					 */
					logger.info("Loading configurations from '{}'.", configuration);
					String configFolder = FileUtil.getDirPath(configuration);
					List<String> additionalConfigs = FileUtil.listAllFilesInDir(configFolder, "(.*?)\\.json$")
							.stream().map(s-> configFolder+"/"+s) // prepend the folder:
							.collect(Collectors.toList());
					loadDefaultConfigs(classesConfig, typeConfig, additionalConfigs);
				}
			}
		}
	}
}
