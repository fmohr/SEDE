package de.upb.sede.webinterfaces.client;

import java.io.*;

/**
 * Implementation of basic client that writes the payload to a file located at
 * destination.
 * 
 * @author aminfaez
 *
 */
public class WriteFileRequest implements BasicClientRequest {

	private final String filepath;
	private final byte[] answer;

	private OutputStream outputStream;

	public WriteFileRequest(String filepath, String answer) {
		this.filepath = filepath;
		this.answer = answer.getBytes();
	}

	@Override
	public OutputStream send() {
		File file = new File(filepath).getAbsoluteFile();
		/*
		 * Create directories up to the file.
		 */
		File parentFile = file.getParentFile();
		if(parentFile != null) {
			parentFile.mkdirs();
		}

		/*
		 * return output stream
		 */
		try {
			outputStream = new FileOutputStream(file);
			return outputStream;
		} catch (FileNotFoundException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public InputStream receive() {
		return new ByteArrayInputStream(answer);
	}

	@Override
	public void close() throws IOException {
		outputStream.close();
	}
}
