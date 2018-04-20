package de.upb.sede.gateway;

import java.net.InetSocketAddress;

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
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		GatewayServer gateWayServer = new GatewayServer();

		server.createContext("/register", new SunHttpHandler(gateWayServer::getExecRegisterHandle));
		server.createContext("/resolve", new SunHttpHandler(gateWayServer::getResolveCompositionHandle));
		server.setExecutor(null); // creates a default executor
		server.start();
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
