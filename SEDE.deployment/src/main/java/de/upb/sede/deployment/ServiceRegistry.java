package de.upb.sede.deployment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.cli.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import de.upb.sede.util.FileUtil;
import de.upb.sede.webinterfaces.server.BasicServerResponse;

public class ServiceRegistry {
	BasicServerResponse responseToExecutionStarter;
	ServiceInventory inventory;

	public static void main(String[] args) throws ParseException {
		ServiceRegistryConfig cmdConfiguration = new ServiceRegistryConfig(args);
		JSONObject configuration = cmdConfiguration.getParsedPathConfigFile().get();
		Collection<ServiceAssemblyAddress> serviceAssemblyAddresses = readServiceAssemblyAddresses(configuration);

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

		ServiceInventory inventory = new ServiceInventory(serviceFileProvider, configProvider, configProvider);
		new ServiceRegistry(serviceAssemblyAddresses, inventory);
	}

	private static Collection<ServiceAssemblyAddress> readServiceAssemblyAddresses(
			JSONObject serviceRegistryConfiguration) {
		Collection<ServiceAssemblyAddress> serviceAssemblyAddresses = new ArrayList<>();

		JSONArray assemblies = (JSONArray) serviceRegistryConfiguration.get("service_assemblies");
		for (Object entryAssembly : assemblies) {
			JSONObject assembly = (JSONObject) entryAssembly;
			JSONArray addresses = (JSONArray) assembly.get("files");
			Collection<String> fileAddresses = new ArrayList<>();
			for (Object entryAddress : addresses) {
				fileAddresses.add((String) entryAddress);
			}
			String typeConfigAddress = (String) assembly.get("typeconf");
			String classConfigAddress = (String) assembly.get("classconf");
			serviceAssemblyAddresses
					.add(new ServiceAssemblyAddress(fileAddresses, classConfigAddress, typeConfigAddress));
		}
		return serviceAssemblyAddresses;
	}

	public ServiceRegistry(Collection<ServiceAssemblyAddress> serviceAssemblyAddresses, ServiceInventory inventory) {
		this.inventory = inventory;
		serviceAssemblyAddresses.forEach(assemblyAddress -> inventory.loadServices(assemblyAddress));
	}

}
