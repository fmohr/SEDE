package de.upb.sede.webinterfaces.server;

import java.io.InputStream;
import java.io.OutputStream;

import de.upb.sede.util.Streams;

public abstract class StringServerResponse implements BasicServerResponse {

	@Override
	public final void receive(InputStream inputStream, OutputStream outputStream) {
		String serverInput = Streams.InReadString(inputStream);
		String serverOutput = receive(serverInput);
		Streams.OutWriteString(outputStream, serverOutput, true);
	}
	
	public abstract String receive(String payload);
}
