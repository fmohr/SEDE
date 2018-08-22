package de.upb.sede.webinterfaces.client;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpURLConnectionClientRequest implements BasicClientRequest {
	private final URL url;
	private final ByteArrayOutputStream payload = new ByteArrayOutputStream();

	private HttpURLConnection httpConnection;


	public HttpURLConnectionClientRequest(String ipAddress, int port, String url) {
		this(ipAddress + ":" + port + (url.startsWith("/")? "" : "/")  + url);
	}

	public HttpURLConnectionClientRequest(String urlAddress) {
		if(!urlAddress.startsWith("http://")){
			urlAddress = "http://" + urlAddress;
		}
		try {
			url = new URL(urlAddress);
		} catch (MalformedURLException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public OutputStream send() {
//		try {
//			return establishHTTPConnection().getOutputStream();
//		} catch (IOException e) {
//			throw new UncheckedIOException(e);
//		}
		return payload;
	}

	@Override
	public InputStream receive() {
//		try {
//			return establishHTTPConnection().getInputStream();
//		} catch (IOException e) {
//			throw new UncheckedIOException(e);
//		}
		try {
			httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setDoInput(true);
			httpConnection.setDoOutput(true);
			httpConnection.setFixedLengthStreamingMode(payload.size());
			httpConnection.connect();

			payload.writeTo(httpConnection.getOutputStream());
			return httpConnection.getInputStream();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * If httpConnection field is null this method will create a new http connection to the specified server.
	 * <p>
	 *     The connnection can both be first written onto. And the it can be read from.
	 *     <p>
	 *         So first invoke  send() and write into the given output stream.
	 *     </p>
	 *     <p>Close the output stream.</p>
	 *     <p>Then invoke receive() and read from the given input stream.</p>
	 *     <p>Close the input stream.</p>
	 * </p>
	 * @return HttpURLConnection to the specified http server.
	 */
	private HttpURLConnection establishHTTPConnection() {
		if(httpConnection == null) {
			try {
				httpConnection = (HttpURLConnection) url.openConnection();
				httpConnection.setDoInput(true);
				httpConnection.setDoOutput(true);
				httpConnection.setChunkedStreamingMode(1<<12);
				httpConnection.connect();
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		} 
		return httpConnection;
	}

	@Override
	public void close() throws IOException {
		if(httpConnection != null)
			httpConnection.disconnect();
	}
}
