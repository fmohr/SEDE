package ai.services.webinterfaces.client;

import java.io.*;

public class ReadFileRequest implements BasicClientRequest {
	private final String filepath;

	private FileInputStream inputStream;

	public ReadFileRequest(String filepath) {
		this.filepath = filepath;
	}

	@Override
	public OutputStream send() {
		return new ByteArrayOutputStream(); // outputstream is discarded.
	}

	@Override
	public InputStream receive() {
		File file = new File(filepath).getAbsoluteFile();

		/*
		 * return input stream
		 */
		try {
			inputStream = new FileInputStream(file);
			return inputStream;
		} catch (FileNotFoundException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}
}
