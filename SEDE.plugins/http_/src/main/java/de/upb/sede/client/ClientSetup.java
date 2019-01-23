package de.upb.sede.client;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.exec.AddressRetriever;
import de.upb.sede.exec.Executor;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.exec.ProxySetup;
import de.upb.sede.gateway.Gateway;
import de.upb.sede.util.ExecutorConfigurationCreator;
import de.upb.sede.util.FileUtil;
import jdk.nashorn.internal.runtime.ParserException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("ALL")
public class ClientSetup {

	private final static Logger logger = LoggerFactory.getLogger(ClientSetup.class);

	/**
	 * Builds a SEDE client from the given ClientProperties.
	 * If the properties define the gatewayaddress, the returned client will be connected to the DISTRIBUTED sede environment.
	 * Else the properties must define service configuration files and client side supported services.
	 *
	 * @param properties loaded from SEDEClient.properties. Contains configurations for the client.
	 * @return a ready to be used CoreClient.
	 * @throws IllegalArgumentException If the properties fail to define mandatory fields.
	 */
	public static CoreClient buildFromProperties(ClientProperties properties) {
		JSONParser jsonParser = new JSONParser();

		logger.info("Building Core Client from properties.");

		ExecutorConfigurationCreator clientConfigCreator = new ExecutorConfigurationCreator();
		String clientId = properties.clientIdPrefix() + Integer.toHexString(UUID.randomUUID().variant());
		logger.info("Setting client id to {}.", clientId);
		clientConfigCreator.withExecutorId(clientId);
		clientConfigCreator.withThreadNumberId(1);
		boolean clientServicesAdded = false;
		if(properties.clientServices() != null) {
			try {
				List<String> supportedService = (List<String>) jsonParser.parse(properties.clientServices());
				if(supportedService.stream().anyMatch(string -> !(string instanceof  String))) {
					/*
					 * One element wasn't string:
					 */
					throw new ParserException("All elements must be string!");
				}
				clientConfigCreator.withSupportedServices(supportedService);
				clientServicesAdded = true;
				logger.info("Client supports local execution for these services: {}", supportedService);
			} catch (ClassCastException | ParseException e) {
				logger.error("Error while parsing {}={}. Must be a json list of strings.", ClientProperties.clientServicesKey, properties.clientServices(), e);
			}
		}

		ExecutorConfiguration clientExecConfig = ExecutorConfiguration.parseJSON(clientConfigCreator.toString());

		if(properties.gatewayAddress() != null) {
			/*
			 * DISTRIBUTED SEDE ENV
			 */
			String gatewayAdress = properties.gatewayAddress();
			if(properties.gatewayPort() != null) {
				gatewayAdress += ":" + properties.gatewayPort();
			}
			logger.info("Embedding client into DISTRIBUTED environment as {}={} was defined.",
					ClientProperties.gatewayAddressKey, gatewayAdress);
			int clientPort = properties.clientPort();
			logger.info("Starting Client http executor; listens to port={}.", clientPort);
			ExecutorHttpServer server = new ExecutorHttpServer(clientExecConfig, "127.0.0.1", clientPort);
			String clientAddress = properties.clientPublicAddress();
			if(clientAddress != null) {
				if(properties.clientPublicPort() != null) {
					clientAddress += ":" + properties.clientPublicPort();
				}
				logger.info("Setting client public address to {}.", clientAddress);
				server.setHostAddress(clientAddress);
			}
			AddressRetriever.enablePlugin(server);
			if(properties.proxyExecutorAddress() != null) {
				System.getenv().put("PROXY_ADDRESS", properties.proxyExecutorAddress());
			}
			ProxySetup.enablePlugin(server);

			CoreClient coreClient = HttpCoreClient.createWithGiven(server.getBasisExecutor(), gatewayAdress);
			logger.info("Successfullt created DISTRIBUTED SEDE client.");
			return coreClient;
		} else if(properties.serviceConfigFiles() != null && clientServicesAdded){
			/*
			 * LOCAL SEDE ENV
			 */
			logger.info("Setting up client with LOCAL environment as `{}` was NOT defined but {}={} and {}={} are set.",
					ClientProperties.gatewayAddressKey,
					ClientProperties.serviceConfigFilesKey, properties.serviceConfigFiles(),
					ClientProperties.clientServicesKey, properties.clientServices());

			List<String> gatewayServiceFiles = null;
			try {
				gatewayServiceFiles = (List<String>) jsonParser.parse(properties.serviceConfigFiles());
				if(gatewayServiceFiles.stream().anyMatch(string -> !(string instanceof  String))) {
					/*
					 * One element wasn't string:
					 */
					throw new ParserException("All elements must be string!");
				}
			} catch (ParseException e) {
				logger.error("Error while parsing {}={}: ", properties.serviceConfigFilesKey, properties.serviceConfigFiles(), e);
				throw new IllegalArgumentException("Cannot parse: " + properties.serviceConfigFilesKey  + "=" + properties.serviceConfigFiles(), e);
			}
			ClassesConfig classesConfig = new ClassesConfig();
			OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
			List<String> classConfigurations = new ArrayList<>(),
					     typeConfigurations = new ArrayList<>();

			for (String serviceFile : gatewayServiceFiles) {
				logger.info("Adding `{}` to gateway configurations.", serviceFile);
				try{
					String serviceFileContent = FileUtil.readResourceAsString(serviceFile);
					if(serviceFile.endsWith("classconf.json")) {
						classConfigurations.add(serviceFileContent);
					} else if(serviceFile.endsWith("typeconf.json")) {
						typeConfigurations.add(serviceFileContent);
					} else {
						throw new IllegalArgumentException("File ending not recognized. Must be either `typeconf.json` or `classconf.json`.");
					}
				} catch (Exception ex) {
					logger.warn("Error reading config file `{}` (Continueing) :", serviceFile, ex);
				}
			}

			try{
				classesConfig.appendConfigFromJsonStrings(classConfigurations.toArray(new String[0]));
				typeConfig.appendConfigFromJsonStrings(typeConfigurations.toArray(new String[0]));
			} catch(Exception ex) {
				logger.error("Couldnt add config files to gateway: " , ex);
				throw new IllegalArgumentException(ex);
			}

			Gateway localGateway = new Gateway(classesConfig, typeConfig);
			Executor clientExecutor = new Executor(clientExecConfig);
			CoreClient coreClient = new CoreClient(clientExecutor, localGateway::resolve);
			logger.info("Successfullt created LOCAL SEDE client.");
			return coreClient;
		} else {
			logger.error("ClientProperties has neither set `{}` not `{}` and `{}`. Cannot create client without these properties.",
					ClientProperties.gatewayAddressKey, ClientProperties.serviceConfigFilesKey, ClientProperties.clientServicesKey);
			throw new IllegalArgumentException("ClientProperties has mandatory fields with null values: "
					+ ClientProperties.gatewayAddressKey
					+ ", " + ClientProperties.serviceConfigFilesKey
					+ ", " + ClientProperties.clientServicesKey);
		}
	}
}
