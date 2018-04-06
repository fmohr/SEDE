package de.upb.sede.exec;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.upb.sede.services.Language;

public class ServiceFileManager extends FileManager{
	private File serviceLocation;

	public ServiceFileManager(File serviceLocation) {
		this.serviceLocation = serviceLocation;
		mkDir(serviceLocation);
	}
	
	@Override
	public byte[] load(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(byte[] data, String fileName) {
		try {
			File destination = new File(serviceLocation + File.pathSeparator + fileName);
			if (destination.exists())
				System.out.println("Overwriting file: " + destination.getName());
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(destination));
			output.write(data, 0, data.length);
			output.close();
			System.out.println(">>> recv file " + fileName + " (" + (data.length >> 10) + " kiB)");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
