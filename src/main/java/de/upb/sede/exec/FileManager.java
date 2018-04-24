package de.upb.sede.exec;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

	public abstract byte[] load(String fileName);
}
