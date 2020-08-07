package de.upb.sede.gateway;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;

import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.server.SunHttpHandler;

public class WhatIsMyAddressHandler extends SunHttpHandler {

	public static void enablePlugin(GatewayHttpServer server) {
		server.addHandle("/what-is-my-addr", new WhatIsMyAddressHandler());
	}

	private static final Logger logger = LoggerFactory.getLogger(WhatIsMyAddressHandler.class);

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
