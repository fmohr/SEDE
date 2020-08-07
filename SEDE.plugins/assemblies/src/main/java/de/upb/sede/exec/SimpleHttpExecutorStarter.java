package de.upb.sede.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.upb.sede.config.ExecutorConfiguration;
import de.upb.sede.util.WebUtil;

public class SimpleHttpExecutorStarter {
	private static final Logger logger = LoggerFactory.getLogger(SimpleHttpExecutorStarter.class);
	public static void main(String[] args) {
		if(args.length < 2) {
			throw new RuntimeException("Please provide at least 2 arguments:" +
					"\n\t - The path to the executor configuration." +
					"\n\t - The port of the http executor." +
					"\n\t - (Optional) The address of the http gateway server.");
		}
		String pathToConfig = args[0];
		ExecutorConfiguration configuration = ExecutorConfiguration.parseJSONFromFile(pathToConfig);

		String host = WebUtil.HostPublicIpAddress();

		int port = Integer.parseInt(args[1]);


		logger.info("Starting SEDE Http executor. \n\t config: {} \n\t host: {} \n\t port: {}",
				pathToConfig, host, port);

		ExecutorHttpServer executorHttpServer = new ExecutorHttpServer(configuration, host, port);

		if(args.length >= 3) {
			String gatewayAddress = args[2];
			logger.info("Http executor registers to gateway on {}.", gatewayAddress);
			executorHttpServer.registerToGateway(gatewayAddress);
		}

	}
}
