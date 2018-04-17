package de.upb.sede.webinterfaces.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPClientRequest implements BasicClientRequest {
	URL url;

	public HTTPClientRequest(String urlAddress) throws MalformedURLException {
		url = new URL(urlAddress);
	}

	@Override
	public OutputStream send() {
		try {
			HttpURLConnection httpConnection = establishHTTPConnection(url);
			return httpConnection.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public InputStream receive() {
		try {
			HttpURLConnection httpConnection = establishHTTPConnection(url);
			return httpConnection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private HttpURLConnection establishHTTPConnection(URL url) throws IOException {
		HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
		httpConnection.setDoInput(true);
		httpConnection.setDoOutput(true);
		httpConnection.connect();
		return httpConnection;
	}
}
