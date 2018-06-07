package de.upb.sede.deployment;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.exec.ExecutorConfiguration;

public class ServiceInventory {

	List<ServiceAssembly> serviceAssamblies;

	ServiceFileProvider serviceFileProvider;
	ConfigurationProvider classConfigProvider;
	ConfigurationProvider typeConfigProvider;

	public ServiceInventory(ServiceFileProvider serviceFileProvider, ConfigurationProvider classConfigProvider,
			ConfigurationProvider typeConfigProvider) {
		this.serviceFileProvider = serviceFileProvider;
		this.classConfigProvider = classConfigProvider;
		this.typeConfigProvider = typeConfigProvider;
	}

	public Collection<ServiceAssembly> getServiceAssembliesFor(ExecutorConfiguration execConfig) {
		Collection<ServiceAssembly> neededAssemblies = serviceAssamblies.stream()
				.filter(assembly -> assembly.getClassConfiguration().classesKnown().stream()
						.anyMatch(className -> execConfig.getSupportedServices().contains(className)))
				.collect(Collectors.toSet());
		return neededAssemblies;
	}

	public void loadServices(ServiceAssemblyAddress serviceAssemblyAddress) {
		Collection<byte[]> serviceFiles = serviceAssemblyAddress.getAddressesOfServiceFiles().stream()
				.map(address -> serviceFileProvider.provideFile(address)).collect(Collectors.toSet());
		String classConfiguration = classConfigProvider
				.provideConfiuration(serviceAssemblyAddress.getAddrOfTypeConfig());
		String typeConfigJSON = typeConfigProvider.provideConfiuration(serviceAssemblyAddress.getAddrOfTypeConfig());
		ClassesConfig classConfig = new ClassesConfig();
		classConfig.appendConfigFromJsonStrings(classConfiguration);
		OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
		typeConfig.appendConfigFromJsonStrings(typeConfigJSON);
		serviceAssamblies.add(new ServiceAssembly(serviceFiles, classConfig, typeConfig));
	}
}
