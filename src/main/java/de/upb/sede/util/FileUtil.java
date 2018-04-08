package de.upb.sede.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

	public static String readFileAsString(String filePath) {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(filePath));
			return new String(encoded, Charset.defaultCharset());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}