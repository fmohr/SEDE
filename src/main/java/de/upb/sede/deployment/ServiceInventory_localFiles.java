package de.upb.sede.deployment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import de.upb.sede.util.FileUtil;

public class ServiceInventory_localFiles extends ServiceInventory {

	public ServiceInventory_localFiles() {
		ConfigurationProvider configProvider = address -> {
			String content = "";
			content = FileUtil.readFileAsString(address);
			return content;
		};
		classConfigProvider = configProvider;
		typeConfigProvider = configProvider;
		serviceFileProvider = address -> {
			byte[] content = new byte[0];
			try {
				content = Files.readAllBytes(Paths.get(address));
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("File: '" + address + "' could not be read.");
			}
			return content;
		};
	}
}
