package de.upb.sede.exec;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public abstract class FileManager {
	private static Logger logger = Logger.getLogger(FileManager.class);

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

	public abstract void save(char[] dataStream, String fileName);

	public abstract String load();
}
