package de.upb.sede.exec;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.upb.sede.services.Language;

public class ServiceFileManager extends FileManager{
	private static final String JAVA_DIR = "java_services";
	private static final String C_DIR = "c_services";
	private static final String PYTHON_DIR = "python_services";
	private File serviceLocation, cLocation, javaLocation, pythonLocation;

	public ServiceFileManager(File serviceLocation) {
		this.serviceLocation = serviceLocation;
		cLocation = new File(serviceLocation + File.pathSeparator + C_DIR);
		javaLocation = new File(serviceLocation + File.pathSeparator + JAVA_DIR);
		pythonLocation = new File(serviceLocation + File.pathSeparator + PYTHON_DIR);
		mkDir(this.serviceLocation);
		mkDir(cLocation);
		mkDir(javaLocation);
		mkDir(pythonLocation);
	}
	
	/**
	 * Stores the transmitted service in the specific service directory.
	 * 
	 * @param data
	 *            service file as a byte array.
	 * @param filename
	 *            Name of the service that is loaded.
	 * @param language
	 *            Language in which the service is implemented.
	 */
	public void storeService(byte[] data, String filename, Language language) {
		try {
			File langDirectory = getLangDirectory(language);
			File destination = new File(langDirectory + File.pathSeparator + filename);
			if (destination.exists())
				System.out.println("Overwriting file: " + destination.getName());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(destination));
			output.write(data, 0, data.length);
			output.close();
			System.out.println(">>> recv file " + filename + " (" + (data.length >> 10) + " kiB)");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stores the transmitted service in the specific service directory.
	 * 
	 * @param data
	 *            service file as a byte array.
	 * @param filename
	 *            Name of the service that is loaded.
	 */
	public void storeService(byte[] data, String filename) {
		try {
			File langDirectory = getLangDirectory(filename);
			File destination = new File(langDirectory + File.pathSeparator + filename);
			if (destination.exists())
				System.out.println("Overwriting file: " + destination.getName());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(destination));
			output.write(data, 0, data.length);
			output.close();
			System.out.println(">>> recv file " + filename + " (" + (data.length >> 10) + " kiB)");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File loadService(String fileName) throws IOException {
		File langDirectory = getLangDirectory(fileName);
		File service = new File(langDirectory.getAbsolutePath() + File.pathSeparator + fileName);
		if (!service.exists())
			throw new FileNotFoundException(fileName + " not found!");
		return service;
	}

	private File getLangDirectory(Language language) throws IOException {
		switch (language) {
		case C:
			return cLocation;
		case JAVA:
			return javaLocation;
		case PYTHON:
			return pythonLocation;
		default:
			throw new IOException("Unknown language specified");
		}
	}

	private File getLangDirectory(String filename) throws IOException {
		String fileExtension = getFileExtension(filename);
		switch (fileExtension) {
		case ".so":
			return cLocation;
		case ".jar":
			return javaLocation;
		case ".py":
			return pythonLocation;
		default:
			throw new IOException("Unknown file extension (" + fileExtension + ").");
		}
	}

	public Language getLanguage(File serviceFile) throws IOException {
		switch (getFileExtension(serviceFile.getName())) {
		case ".so":
			return Language.C;
		case ".jar":
			return Language.JAVA;
		case ".py":
			return Language.PYTHON;
		default:
			throw new IOException("Unknown file extension (" + serviceFile.getName() + ").");
		}
	}
}
