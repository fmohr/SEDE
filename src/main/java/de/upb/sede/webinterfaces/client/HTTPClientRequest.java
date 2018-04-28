package de.upb.sede.webinterfaces.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPClientRequest implements BasicClientRequest {
	private final URL url;
	private HttpURLConnection httpConnection;

	public HTTPClientRequest(String urlAddress) {
		try {
			url = new URL(urlAddress);
		} catch (MalformedURLException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public OutputStream send() {
		try {
			return establishHTTPConnection().getOutputStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public InputStream receive() {
		try {
			return establishHTTPConnection().getInputStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private HttpURLConnection establishHTTPConnection() throws IOException {
		if(httpConnection == null) {
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.connect();
		} 
		return httpConnection;
	}
}
