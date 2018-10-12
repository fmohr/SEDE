package de.upb.sede.gateway;

import static de.upb.sede.gateway.GatewayServerStarter.loadDefaultConfigs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.exec.AddressRetriever;
import de.upb.sede.exec.ExecutorHttpServer;
import de.upb.sede.util.FileUtil;

public class Monolith {

	public static void main(String[] args) {
		int gatewayport = 6000;
		int javaExecutorPort = 6001;

		List<String> configsToBeLoaded =
				Arrays.asList("config/builtin-classconf.json",
							"config/builtin-typeconf.json",
							"config/c2imaging-classconf.json",
							"config/c2imaging-typeconf.json",
							"config/imaging-classconf.json",
							"config/imaging-typeconf.json",
							"config/sl-ml-classifiers-classconf.json",
							"config/sl-ml-typeconf.json",
							"config/weka-ml-classifiers-classconf.json",
							"config/weka-ml-clusterers-classconf.json",
							"config/weka-ml-pp-classconf.json",
							"config/weka-ml-typeconf.json");
		

		ClassesConfig classesConfig = new ClassesConfig();
		OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
		loadDefaultConfigs(classesConfig, typeConfig, configsToBeLoaded);

		GatewayHttpServer httpGateway = new GatewayHttpServer(gatewayport, classesConfig, typeConfig);
		
		String executorConfigFile = "monolith_executor_config.json";
		String executorConfigString = FileUtil.readResourceAsString(executorConfigFile);
		ExecutorConfiguration executorConfiguration = ExecutorConfiguration.parseJSON(executorConfigString);

		ExecutorHttpServer httpExecutor = new ExecutorHttpServer(executorConfiguration, "localhost", javaExecutorPort);
		AddressRetriever.enablePlugin(httpExecutor);
	}
}
