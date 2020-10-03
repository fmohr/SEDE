package ai.services.webinterfaces.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import ai.services.util.Streams;

public abstract class StringServerResponse implements HTTPServerResponse {

	/*
		 * Overwrite the stream based receive method by using the newly defined String
		 * based receive method (see below). (non-Javadoc)
		 *
		 * @see de.upb.sede.webinterfaces.server.BasicServerResponse#receive(java.io.
		 * InputStream, java.io.OutputStream)
		 */
	@Override
	public final void receive(Optional<String> url, InputStream inputStream, OutputStream outputStream) {
		String serverInput = Streams.InReadString(inputStream);
		String serverOutput = receive(url, serverInput);
		Streams.OutWriteString(outputStream, serverOutput, true);
	}

	/**
	 * This method offers a String based interface for messaging a server.
	 *
	 * @param payload
	 *            content of a request
	 * @return the response of the request from the server
	 */
	public String receive(Optional<String> url, String payload){
		return receive(payload);
	}

	/**
	 * This method offers a String based interface for messaging a server.
	 *
	 * @param payload
	 *            content of a request
	 * @return the response of the request from the server
	 */
	public abstract String receive(String payload);
}
