package de.upb.sede.webinterfaces.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.upb.sede.util.Streams;

public class FileServerResponse implements BasicServerResponse {

	@Override
	public void receive(InputStream payload, OutputStream answer) {
		String filepath = Streams.InReadString(payload);
		try {
			Files.copy(Paths.get(filepath),
					answer);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
}
