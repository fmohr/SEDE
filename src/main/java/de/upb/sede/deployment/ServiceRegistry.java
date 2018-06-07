package de.upb.sede.deployment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import org.apache.commons.cli.ParseException;
import org.json.simple.JSONObject;

import de.upb.sede.util.FileUtil;
import de.upb.sede.webinterfaces.server.BasicServerResponse;

public class ServiceRegistry {
	BasicServerResponse responseToExecutionStarter;
	ServiceInventory inventory;

	public ServiceRegistry(Collection<ServiceAssemblyAddress> serviceAssemblyAddresses, ServiceInventory inventory) {
		this.inventory = inventory;
		serviceAssemblyAddresses.forEach(assemblyAddress -> inventory.loadServices(assemblyAddress));
	}

	public static void main(String[] args) throws ParseException {
		ServiceRegistryConfig cmdConfiguration = new ServiceRegistryConfig(args);
		cmdConfiguration.getParsedPathConfigFile().ifPresent(ServiceRegistry::readServiceAssemblyAddresses);

		ConfigurationProvider configProvider = address -> {
			return FileUtil.readFileAsString(address);
		};
		ServiceFileProvider serviceFileProvider = address -> {
			byte[] content = new byte[0];
			try {
				content = Files.readAllBytes(Paths.get(address));
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("File: '" + address + "' could not be read.");
			}
			return content;
		};

		Collection<ServiceAssemblyAddress> tmp = null;
		ServiceInventory inventory = new ServiceInventory(serviceFileProvider, configProvider, configProvider);
		new ServiceRegistry(tmp, inventory);
	}

	private static void readServiceAssemblyAddresses(JSONObject serviceRegistryConfiguration) {

	}
}
