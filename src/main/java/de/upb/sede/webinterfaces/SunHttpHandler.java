package de.upb.sede.webinterfaces;

import java.io.IOException;
import java.util.function.Supplier;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import de.upb.sede.webinterfaces.server.BasicServerResponse;

public class SunHttpHandler implements HttpHandler{
	
	private final Supplier<BasicServerResponse> serverResponder;
	
	public SunHttpHandler(Supplier<BasicServerResponse> serverResponder) {
		this.serverResponder = serverResponder;
	}
	
	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		BasicServerResponse response = serverResponder.get();
		response.receive(httpExchange.getRequestBody(), httpExchange.getResponseBody());
	}

}
