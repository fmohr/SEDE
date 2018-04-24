package de.upb.sede.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.upb.sede.webinterfaces.client.WriteFileRequest;

/**
 * Defines methods to access files reading and writing their content. Use this
 * class when you don't want to bother with checked IO-exceptions.
 * 
 * @author aminfaez
 *
 */
public class FileUtil {

	/**
	 * Returns the content of the file located at the given file path as a string.
	 * 
	 * @param filePath
	 *            path to the file to be read from
	 * @return Content of the file as a string
	 *
	 * @throws UncheckedIOException
	 *             if an I/O error occurs reading from the file
	 */
	public static String readFileAsString(String filePath) {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(filePath));
			String fileContent = new String(encoded, Charset.defaultCharset());
			return fileContent.replaceAll("\r\n", "\n");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Write the given content to the given file. Creates the directory and the file
	 * if it doesn't exist. Deletes (overwrites) the old content if the file already
	 * exists
	 * 
	 * Uses WriteFileRequest.
	 * 
	 * @param filePath
	 *            path to the file to be written into
	 * @param fileContent
	 *            content that will be written to the file
	 * @return true, if the writing was successful
	 * 
	 * @throws UncheckedIOException
	 *             if an I/O error occurs writing to the file
	 */
	public static boolean writeStringToFile(String filePath, String fileContent) {
		WriteFileRequest writeFile = new WriteFileRequest(filePath, "success");
		return writeFile.send(fileContent).equals("success");
	}

}