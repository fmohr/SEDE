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
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GatewayServerStarter {
	private final static Logger logger = LogManager.getLogger();
	private final ServerCommandListener commandListener;
	private final GatewayHttpServer gatewayServer;



	private GatewayServerStarter(int serverPort) {
		ClassesConfig classesConfig = new ClassesConfig();
		OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
		loadDefaultConfigs(classesConfig, typeConfig);

		gatewayServer = new GatewayHttpServer(serverPort, classesConfig, typeConfig);

		commandListener = new ServerCommandListener(gatewayServer);
		commandListener.addCommandHandle("load-classconf", new GatewayServerStarter.LoadClassConfig());
		commandListener.addCommandHandle("load-typeconf", new GatewayServerStarter.LoadTypeConfig());
		commandListener.listenEndlessly();


	}

	private void loadDefaultConfigs(ClassesConfig classesConfig, OnthologicalTypeConfig typeConfig) {
		File defaultConfigDir = new File("configs");
		if(!defaultConfigDir.isDirectory()){
			/*
				if the default folder for configuration doesn't exist or is file, fail:
			 */
			logger.info("'configs' directory either doesn't exist. Cannot load any config off the bat.");
			return;
		}
		/*
			look for json files ending with classconf.json in configs directory
			and try to append them to the class configuration:
		 */
		for(String classConfigPath : FileUtil.listAllFilesInDir("configs", "(.*?)classconf\\.json$")){
			try{
				logger.info("Loading classes config: {} .." , classConfigPath);
				classesConfig.appendConfigFromFiles(classConfigPath);
			}
			catch(Exception ex) {
				logger.error("Problem loading class configuration from {}:\n", classConfigPath, ex);
			}
		}
		/*
			look for json files ending with typeconf.json in configs directory
			and try to append them to the type configuration:
		 */
		for(String typeConfigPath : FileUtil.listAllFilesInDir("configs", "(.*?)typeconf\\.json$")){
			try{
				logger.info("Loading type config: {} .." , typeConfigPath);
				typeConfig.appendConfigFromFiles(typeConfigPath);
			}
			catch(Exception ex) {
				logger.error("Problem loading class configuration from {}:\n", typeConfigPath, ex);
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		if(args == null || args.length != 1) {
			throw new RuntimeException("Need 1 input argument: port");
		}
		int serverPort = Integer.parseInt(args[0]);

		logger.info("Starting gateway with: \nport:{}", serverPort);

		new GatewayServerStarter(serverPort);
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
				logger.error("Loading class config {} failed.\nError:\n", classConfPath, Streams.ErrToString(ex));
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
				gatewayServer.getClassesConfig().appendConfigFromFiles(typeConfPath);
				logger.info("Added config {} successfully.", typeConfPath);
				return "Successfully added following typeconfig: " + typeConfPath;
			} catch(Exception ex) {
				logger.error("Loading type config {} failed.\nError:\n", typeConfPath, Streams.ErrToString(ex));
				return "Error trying to add following typeconfig: " + typeConfPath;
			}
		}
	}
}
