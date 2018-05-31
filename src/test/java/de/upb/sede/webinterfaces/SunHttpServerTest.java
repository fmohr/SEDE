package de.upb.sede.webinterfaces;

import com.sun.net.httpserver.HttpServer;
import de.upb.sede.util.Streams;
import de.upb.sede.webinterfaces.client.HTTPClientRequest;
import de.upb.sede.webinterfaces.server.HTTPServerResponse;
import de.upb.sede.webinterfaces.server.SunHttpHandler;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.util.Optional;

public class SunHttpServerTest {
	@Test
	public void testEchoURLServer() {
		HttpServer server;
		int port = 30000;
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}

		server.createContext("/echo/", new SunHttpHandler(()->new HTTPServerResponse() {
			@Override
			public void receive(Optional<String> url, InputStream payload, OutputStream answer) {
				Streams.OutWriteString(answer, url.get(), true);
			}
		}));
		server.setExecutor(null); // creates a default executor
		server.start();
		String url = "/echo/d5f60134-7fd8-42a4-930c-d3b130f0e7a9/ p/Arr";
		HTTPClientRequest request = new HTTPClientRequest("localhost", port, url);
		String answer = request.send("123");
		Assert.assertEquals(url, answer);
	}
}
