package de.upb.sede.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Streams {
	/**
	 * Reads the content in the given input stream and converts it to a string.
	 */
	public static String InReadString(InputStream is) {

		try (InputStream inputStream = is) {

			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toString(StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Writes the given content into the given outputstream. if close is true,
	 * closes the stream afterwards.
	 */
	public static void OutWriteString(OutputStream outstream, String content, boolean close) {

		try {
			outstream.write(content.getBytes(StandardCharsets.UTF_8.name()));
			if (close) {
				outstream.close();
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
