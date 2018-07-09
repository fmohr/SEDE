package de.upb.sede.util;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import de.upb.sede.webinterfaces.client.WriteFileRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Defines methods to access files reading and writing their content. Use this
 * class when you don't want to bother with checked IO-exceptions.
 * 
 * @author aminfaez
 *
 */
public class FileUtil {

	private static Logger logger = LogManager.getLogger(FileUtil.class);

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
			encoded = Files.readAllBytes(Paths.get(Objects.requireNonNull(filePath)));
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

	/**
	 * Creates a list of all file names in the given directory that match the given
	 * regex. The list will not contain sub-directories even if their name match the
	 * given regex.
	 * 
	 * @param dirPath
	 *            path to the Directory that is looked into
	 * @param regex
	 *            regular expression used as a filter over the files. If null all
	 *            files in the directory are returned.
	 * @return list of all files
	 */
	public static List<String> listAllFilesInDir(String dirPath, String regex) {

		File directory = new File(Objects.requireNonNull(dirPath));
		if (!directory.isDirectory()) {
			throw new RuntimeException("The given path \"" + dirPath + "\" is not a directory.");
		}

		/*
		 * prepare the regular expression filter:
		 */
		Pattern filterPattern;
		if (regex != null) {
			filterPattern = Pattern.compile(regex);
		} else {
			filterPattern = Pattern.compile("(.*?)"); // match all
		}

		/*
		 * iterate over files in the given directory:
		 */
		List<String> listedFiles = new ArrayList<>();
		File[] files = directory.listFiles();
		for (File file : files) {
			if (filterPattern.matcher(file.getName()).matches() && file.isFile()) {
				listedFiles.add(file.getName());
			}
		}
		return listedFiles;
	}




	protected static void mkDir(File file) {
		if (!file.exists()) {
			logger.info("Creating directory: \"" + file.getAbsolutePath() + "\"");
			file.mkdir();
		}
	}

	protected static String getFileExtension(String filename) throws IOException {
		try {
			return filename.substring(filename.lastIndexOf("."));
		} catch (IndexOutOfBoundsException e) {
			logger.debug("File: \"" + filename + "\" does not have a file extension.", e);
			return "";
		}
	}

	public void save(byte[] data, String filePath) {
		try {
			File destination = new File(filePath);
			if (destination.exists())
				System.out.println("Overwriting file: " + destination.getName());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(destination));
			output.write(data, 0, data.length);
			output.close();
			System.out.println(">>> recv file " + filePath + " (" + (data.length >> 10) + " kiB)");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}