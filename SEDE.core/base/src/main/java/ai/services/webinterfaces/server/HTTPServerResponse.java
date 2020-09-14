package de.upb.sede.webinterfaces.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public interface HTTPServerResponse extends BasicServerResponse{

	public void receive(Optional<String> url, InputStream payload, OutputStream answer);

	@Override
	default void receive(InputStream payload, OutputStream answer) {
		receive(Optional.empty(), payload, answer);
	}
}
