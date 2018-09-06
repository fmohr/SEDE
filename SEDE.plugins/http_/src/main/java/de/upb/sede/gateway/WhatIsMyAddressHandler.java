package de.upb.sede.gateway;

import com.sun.net.httpserver.HttpExchange;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.ImServer;
import de.upb.sede.webinterfaces.server.SunHttpHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Optional;

public class WhatIsMyAddressHandler extends SunHttpHandler {

	public static void enablePlugin(GatewayHttpServer server) {
		server.addHandle("/what-is-my-addr", new WhatIsMyAddressHandler());
	}

	private final static Logger logger = LogManager.getLogger();

	private WhatIsMyAddressHandler() {
		super(null);
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		logger.info("Received request to reverse lookup the address of the client.");
		try {
			InetSocketAddress remoteAddress = httpExchange.getRemoteAddress();
			String address;
			if(remoteAddress.getHostString()==null){
				throw new RuntimeException("Couldnt Resolve remote address.");
			}
			address = remoteAddress.getHostString();
			httpExchange.sendResponseHeaders(200, 0);
			logger.info("Client's address: " + address);
			Streams.OutWriteString(httpExchange.getResponseBody(), address, true);
		} catch(RuntimeException ex) {
			String requester = httpExchange.getRemoteAddress().getHostName();
			String port = "" + httpExchange.getRemoteAddress().getPort();
			String url = httpExchange.getRequestURI().getPath();
			logger.error("Error handle of request " + url + " from entity "+ requester  + ":" +  port + "\n", ex);
		}
	}
}