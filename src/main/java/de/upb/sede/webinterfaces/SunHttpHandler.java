package de.upb.sede.webinterfaces;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

import de.upb.sede.webinterfaces.server.HTTPServerResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.upb.sede.webinterfaces.server.BasicServerResponse;

public class SunHttpHandler implements HttpHandler {
	private final static Logger logger = LogManager.getLogger();
	
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
			logger.error("Error handle of request from entity: " + requester + ":" +  port, ex);
			throw ex;
		}
	}

}
