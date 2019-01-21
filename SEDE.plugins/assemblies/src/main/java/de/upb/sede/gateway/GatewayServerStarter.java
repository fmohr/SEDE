package de.upb.sede.gateway;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.PluginUtil;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.server.TerminalCommandListener;
import de.upb.sede.webinterfaces.server.ImServerCommandListener;
import de.upb.sede.webinterfaces.server.ServerCommandListeners;
import de.upb.sede.webinterfaces.server.StdShellCommands;

public class GatewayServerStarter {
	private final static Logger logger = LoggerFactory.getLogger(GatewayServerStarter.class);

	public static void main(String[] args) throws InterruptedException {

		if (args == null || args.length == 0) {
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
		for (String configuration : configsToBeLoaded) {
			loadDefaultConfiguration(classesConfig, typeConfig, configuration);
		}
	}

	static void loadDefaultConfiguration(ClassesConfig classesConfig, OnthologicalTypeConfig typeConfig,
								   String configuration) {
		File configFile = new File(configuration).getAbsoluteFile();

		/*
		 * look for json files ending with classconf.json or typeconf.json in the given
		 * list and try to append them to the class/type configuration:
		 */
		if (configFile.isDirectory()) {
			/*
			 * load every class conf file from the given folder:
			 */
			logger.info(
					"Config file '{}' is a directory, loading every classconf and typeconf files in it...",
					configuration);
			List<String> additionalConfigs = FileUtil.listAllFilesInDir(configFile.getAbsolutePath(), "(.*?)\\.json$").stream()
					.map(s -> configFile + "/" + s) // prepend the folder:
					.collect(Collectors.toList());
			loadDefaultConfigs(classesConfig, typeConfig, additionalConfigs);
		} else{

			String configStringContent;
			if(configFile.exists()) {
				configStringContent = FileUtil.readFileAsString(configuration);
			} else {
				try{
					logger.info("Reading config file {} from classpath..", configuration);
					configStringContent = FileUtil.readResourceAsString(configuration);
				} catch (Exception ex) {
					logger.error(
							"Config file '{}' could not be parsed. (Skipping it).",
							configuration, ex);
					return;
				}
			}
			if (configuration.matches("(.*?)classconf\\.json$")) {
				try {
					classesConfig.appendConfigFromJsonStrings(configStringContent);
					logger.info("Added classes config from: {}", configuration);
				} catch (Exception ex) {
					logger.error("Problem loading class configuration from {}:\n", configuration, ex);
				}
			} else if (configuration.matches("(.*?)typeconf\\.json$")) {
				try {
					typeConfig.appendConfigFromJsonStrings(configStringContent);
					logger.info("Added type config from: {}", configuration);
				} catch (Exception ex) {
					logger.error("Problem loading type configuration from {}:\n", configuration, ex);
				}
			}
			else{
				logger.error("Cannot differentiate configuration {}, as it doesn't end with classconf nor typeconf.", configuration);
			}
		}
	}
}
