package de.upb.sede.deployment;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;

public abstract class ServiceInventory {

	List<ServiceAssembly> serviceAssamblies;

	ServiceFileProvider serviceFileProvider;
	ClassConfigurationProvider classConfigProvider;
	TypeConfigurationProvider typeConfigProvider;

	abstract ServiceAssembly getServiceAssemblyFor(JSONObject execConfiguration);

	public void loadServices(Collection<ServiceAssemblyLoad> serviceAssemblyLoad) throws ParseException {
		for (ServiceAssemblyLoad addressObject : serviceAssemblyLoad) {
			Collection<byte[]> serviceFiles = addressObject.getAddressesOfServiceFiles().stream()
					.map(address -> serviceFileProvider.provideFile(address)).collect(Collectors.toSet());
			String classConfiguration = classConfigProvider.provideConfiuration(addressObject.getAddrOfTypeConfig());
			String typeConfigJSON = typeConfigProvider.provideConfiuration(addressObject.getAddrOfTypeConfig());
			ClassesConfig classConfig = new ClassesConfig();
			classConfig.appendConfigFromJsonStrings(classConfiguration);
			OnthologicalTypeConfig typeConfig = new OnthologicalTypeConfig();
			typeConfig.appendConfigFromJsonStrings(typeConfigJSON);
			serviceAssamblies.add(new ServiceAssembly(serviceFiles, classConfig, typeConfig));
		}
	}
}
