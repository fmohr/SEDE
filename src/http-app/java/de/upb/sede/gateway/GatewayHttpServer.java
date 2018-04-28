package de.upb.sede.gateway;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.parser.JSONParser;

import com.sun.net.httpserver.HttpServer;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.requests.resolve.ResolveRequest;
import de.upb.sede.webinterfaces.SunHttpHandler;
import de.upb.sede.webinterfaces.server.StringServerResponse;

public final class GatewayHttpServer extends Gateway {

	private final static Logger logger = LogManager.getLogger();
	private final static int DEFAULT_PORT = 6060;

	private final HttpServer server;

	public GatewayHttpServer(int port, ClassesConfig classesConfig, OnthologicalTypeConfig typeConfig) {
		super(classesConfig, typeConfig);

		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		server.createContext("/register", new SunHttpHandler(ExecutorRegistrationHandler::new));
		server.createContext("/resolve", new SunHttpHandler(ResolveCompositionHandler::new));
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	public static void main(String[] args) throws Exception {
		logger.info("Starting Gateway-HTTPServer....");
		int port;
		if (args.length > 0) {
			String portInput = args[0];
			try {
				port = Integer.parseInt(portInput);
			} catch (NumberFormatException ex) {
				port = DEFAULT_PORT;
				logger.error("Error during parsing port: " + portInput);
			}
		} else {
			port = DEFAULT_PORT;
			logger.warn("Port was not passed as an argument. Using default: " + DEFAULT_PORT);
		}

		String[] classConfigPaths = { "testrsc/config/test-classconf.json" };
		String[] typeConfigPaths = { "testrsc/config/test-classconf.json" };

		logger.debug("Using {} class configs and {} type configs.", classConfigPaths, typeConfigPaths);

		ClassesConfig classConf = new ClassesConfig(classConfigPaths);
		OnthologicalTypeConfig typeConf = new OnthologicalTypeConfig(typeConfigPaths);

		new GatewayHttpServer(port, classConf, typeConf);
	}

	class ExecutorRegistrationHandler extends StringServerResponse {

		@SuppressWarnings("unchecked")
		@Override
		public String receive(String jsonRegistration) {
			logger.debug("Received executor registration.");
			/*
			 * TODO: Do validation before parsing.
			 */

			ExecutorRegistration execRegister = new ExecutorRegistration();
			execRegister.fromJsonString(jsonRegistration);
			register(execRegister);
			return "Registration Successfull";
		}

	}

	class ResolveCompositionHandler extends StringServerResponse {
		@Override
		public String receive(String jsonResolveRequest) {
			logger.debug("Received executor registration.");
			/*
			 * TODO: Do validation before parsing.
			 */
			/*
			 * parse request:
			 */
			ResolveRequest resolveRequest = new ResolveRequest();
			resolveRequest.fromJsonString(jsonResolveRequest);
			/*
			 * calculate the resolved graph:
			 */
			GatewayResolution resolution = resolve(resolveRequest);
			return resolution.toJson().toJSONString();
		}
	}

}
