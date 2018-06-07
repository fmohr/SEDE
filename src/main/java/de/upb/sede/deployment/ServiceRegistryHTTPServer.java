package de.upb.sede.deployment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.net.httpserver.HttpServer;

import de.upb.sede.exec.ExecutorConfiguration;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.HTTPServerResponse;
import de.upb.sede.webinterfaces.server.ImServer;
import de.upb.sede.webinterfaces.server.SunHttpHandler;

public class ServiceRegistryHTTPServer extends ServiceRegistry implements ImServer {
	private final String hostAddress;
	private final HttpServer server;

	public ServiceRegistryHTTPServer(Collection<ServiceAssemblyAddress> serviceAssemblyAddresses,
			ServiceInventory inventory, String hostAddress, int port) {
		super(serviceAssemblyAddresses, inventory);
		this.hostAddress = hostAddress + ":" + port;

		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		addHandle("/deploy", DeploymentServerResponse::new);

		server.setExecutor(null); // creates a default executor
		server.start();
	}
	
	public String getHostAddress() {
		return hostAddress;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addHandle(String context, Supplier<HTTPServerResponse> serverResponder) {
		server.createContext(context, new SunHttpHandler(serverResponder));
	}

	class DeploymentServerResponse implements HTTPServerResponse {
		@Override
		public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
			receive(payload, answer);
		}

		@Override
		public void receive(InputStream payload, OutputStream answer) {
			String receivedExecutorConfig = Streams.InReadString(payload);
			ExecutorConfiguration executorConfig = ExecutorConfiguration.parseJSON(receivedExecutorConfig);
			inventory.getServiceAssembliesFor(executorConfig);
		}
	}
}
