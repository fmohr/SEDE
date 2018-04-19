package de.upb.sede.webinterfaces.server;

import java.io.InputStream;
import java.io.OutputStream;

import de.upb.sede.util.Streams;

public abstract class StringServerResponse implements BasicServerResponse {

	
	/*
	 * Overwrite the stream based receive method by using the newly defined String based receive method (see below).
	 * (non-Javadoc)
	 * @see de.upb.sede.webinterfaces.server.BasicServerResponse#receive(java.io.InputStream, java.io.OutputStream)
	 */
	@Override
	public final void receive(InputStream inputStream, OutputStream outputStream) {
		String serverInput = Streams.InReadString(inputStream);
		String serverOutput = receive(serverInput);
		Streams.OutWriteString(outputStream, serverOutput, true);
	}
	
	/**
	 * This method offers a String based interface for messaging a server.
	 * @param payload content of a request
	 * @return the response of the request from the server
	 */
	public abstract String receive(String payload);
}
