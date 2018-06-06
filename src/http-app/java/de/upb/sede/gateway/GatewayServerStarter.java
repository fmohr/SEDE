package de.upb.sede.gateway;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.ServerCommandListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GatewayServerStarter {
	private final static Logger logger = LogManager.getLogger();
	private final ServerCommandListener commandListener;
	private final GatewayHttpServer gatewayServer;



	private GatewayServerStarter(int serverPort, List<String> configurationsToLoad) {
		ClassesConfig classesConfig = new ClassesConfig();
		OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
		loadDefaultConfigs(classesConfig, typeConfig, configurationsToLoad);

		gatewayServer = new GatewayHttpServer(serverPort, classesConfig, typeConfig);

		commandListener = new ServerCommandListener(gatewayServer);
		commandListener.addCommandHandle("load-classconf", new LoadClassConfig());
		commandListener.addCommandHandle("load-typeconf", new LoadTypeConfig());
		commandListener.addCommandHandle("list-services", new ListServices());
		commandListener.addCommandHandle("list-types", new ListTypes());
		commandListener.listenEndlessly();
	}

	private void loadDefaultConfigs(ClassesConfig classesConfig, OnthologicalTypeConfig typeConfig,
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
				logger.error("Config file '{}' doesn't end with classconf.json or typeconf.json. Cannot add it to the configurations.");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {

		if(args == null || args.length ==0) {
			throw new RuntimeException("Need at least 1 input argument: port");
		}
		int serverPort = Integer.parseInt(args[0]);
		
		List<String> configsToBeLoaded = new ArrayList<>(Arrays.asList(args));
		configsToBeLoaded.remove(0); // remove the port


		logger.info("Starting gateway with: \nport:{}\nconfigurations:{}", serverPort, configsToBeLoaded);

		new GatewayServerStarter(serverPort, configsToBeLoaded);
	}

	class LoadClassConfig implements Function<List<String>, String> {

		@Override
		public String apply(List<String> inputs) {
			if(inputs == null || inputs.isEmpty()) {
				return "Provide the path of the class configurations as the input.";
			}
			String classConfPath = inputs.stream().collect(Collectors.joining("/"));
			logger.info("Trying to load the following classes config: {}", classConfPath);
			try{
				gatewayServer.getClassesConfig().appendConfigFromFiles(classConfPath);
				logger.info("Added config {} successfully.", classConfPath);
				return "Successfully added following classconfig: " + classConfPath;
			} catch(Exception ex) {
				logger.error("Loading class config {} failed.\nError:\n", classConfPath, ex);
				return "Error trying to add following classconfigs: " + classConfPath;
			}
		}
	}
	class LoadTypeConfig implements Function<List<String>, String> {

		@Override
		public String apply(List<String> inputs) {
			if(inputs == null || inputs.isEmpty()) {
				return "Provide the path of the type configurations as inputs.";
			}
			String typeConfPath = inputs.stream().collect(Collectors.joining("/"));
			logger.info("Trying to load the following type config: {}", typeConfPath);
			try{
				gatewayServer.getTypeConfig().appendConfigFromFiles(typeConfPath);
				logger.info("Added config {} successfully.", typeConfPath);
				return "Successfully added following typeconfig: " + typeConfPath;
			} catch(Exception ex) {
				logger.error("Loading type config {} failed.\nError:\n", typeConfPath, ex);
				return "Error trying to add following typeconfig: " + typeConfPath;
			}
		}
	}


	class ListServices implements Function<List<String>, String> {
		@Override
		public String apply(List<String> inputs) {
			ClassesConfig classesConfig = gatewayServer.getClassesConfig();
			String knownClasses = classesConfig.classesKnown().stream().sorted()
					.collect(Collectors.joining("\n"));
			return knownClasses;
		}
	}

	class ListTypes implements Function<List<String>, String> {
		@Override
		public String apply(List<String> inputs) {
			OnthologicalTypeConfig typeConfig = gatewayServer.getTypeConfig();
			List<String> knownTypes = new ArrayList<>();

			for(String type : typeConfig.knownTypes()){
				knownTypes.add(type + " <-> " + typeConfig.getOnthologicalType(type));
			}
			String returnString = knownTypes.stream().sorted()
					.collect(Collectors.joining("\n"));
			return returnString;
		}
	}
}
