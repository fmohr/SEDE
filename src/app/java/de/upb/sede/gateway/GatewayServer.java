package de.upb.sede.gateway;

import java.net.InetSocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpServer;

import de.upb.sede.composition.gc.ExecutorCoordinator;
import de.upb.sede.config.ClassesConfig;
import de.upb.sede.interfaces.Gateway;
import de.upb.sede.requests.ExecutorRegistration;
import de.upb.sede.requests.Request;
import de.upb.sede.requests.resolve.GatewayResolution;
import de.upb.sede.webinterfaces.SunHttpHandler;
import de.upb.sede.webinterfaces.server.BasicServerResponse;

public class GatewayServer implements Gateway {
	
	private final static Logger logger = LogManager.getLogger();
	private final static int DEFAULT_PORT =  6060;

	private final ExecutorCoordinator execCoordinator = new ExecutorCoordinator();
	private final ClassesConfig classesConfig;

	public GatewayServer(String... classConfigFilePaths) {
		classesConfig = new ClassesConfig(classConfigFilePaths);
	}

	public BasicServerResponse getExecRegisterHandle() {
		return new ExecutorRegistrationHandler(execCoordinator, classesConfig);
	}

	public BasicServerResponse getResolveCompositionHandle() {
		return new ResolveCompositionHandler(execCoordinator, classesConfig);
	}

	public static void main(String[] args) throws Exception {
		logger.info("Starting Gateway-HTTPServer....");
		int port;
		if(args.length > 0) {
			String portInput = args[0];
			try {
				port = Integer.parseInt(portInput);
			} catch(NumberFormatException ex) {
				port = DEFAULT_PORT;
				logger.info("Error during parsing port: " + portInput);
			}
		} else {
			port = DEFAULT_PORT;
			logger.info("Port was not passed as an argument. Using default: " + DEFAULT_PORT);
		}
		
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		GatewayServer gateWayServer = new GatewayServer();

		server.createContext("/register", new SunHttpHandler(gateWayServer::getExecRegisterHandle));
		server.createContext("/resolve", new SunHttpHandler(gateWayServer::getResolveCompositionHandle));
		server.setExecutor(null); // creates a default executor
		server.start();
		logger.info("Gateway-HTTPServer started. Listening on port: " + port);
	}

	@Override
	public GatewayResolution resolve(Request resolveRequest) {
		return null;
	}

	@Override
	public boolean register(ExecutorRegistration registry) {
		return false;
	}

}
