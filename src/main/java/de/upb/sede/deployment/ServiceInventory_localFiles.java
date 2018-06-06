package de.upb.sede.deployment;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class ServiceInventory_localFiles extends ServiceInventory {

	public ServiceInventory_localFiles() {
		ConfigurationProvider configProvider = address -> {
			String content = "";
			try (FileInputStream input = new FileInputStream(address)) {
				content = IOUtils.toString(input);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("No file: '" + address + "' found.");
			}
			return content;
		};
		classConfigProvider = configProvider;
		typeConfigProvider = configProvider;
		serviceFileProvider = address -> {
			byte[] content = new byte[0];
			try (FileInputStream input = new FileInputStream(address)) {
				content = IOUtils.toByteArray(input);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("No file: '" + address + "' found.");
			}
			return content;
		};
	}
}
