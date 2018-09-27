package de.upb.sede.webinterfaces.server;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class SunHttpHandler implements HttpHandler {
	private final static Logger logger = LoggerFactory.getLogger(SunHttpHandler.class);
	
	private final Supplier<HTTPServerResponse> serverResponder;

	public SunHttpHandler(Supplier<HTTPServerResponse> serverResponder) {
		this.serverResponder = serverResponder;
	}

	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		try {
			HTTPServerResponse response = serverResponder.get();
			httpExchange.sendResponseHeaders(200, 0);
			String path = httpExchange.getRequestURI().getPath();
			response.receive(Optional.of(path), httpExchange.getRequestBody(), httpExchange.getResponseBody());
		} catch(RuntimeException ex) {
			String requester = httpExchange.getRemoteAddress().getHostName();
			String port = "" + httpExchange.getRemoteAddress().getPort();
			String url = httpExchange.getRequestURI().getPath();
			logger.error("Error handle of request " + url + " from entity "+ requester  + ":" +  port + "\n", ex);
		}
	}


}
