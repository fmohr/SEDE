package de.upb.sede.deployment;

import java.util.List;

public class ServiceAssemblyAddress {
	private final List<String> addressesOfServiceFiles;
	private final String addressOfClassConfiguration;
	private final String addressOfTypeConfiguration;

	public ServiceAssemblyAddress(List<String> addressesOfServiceFiles, String addressOfClassConfiguration,
			String addressOfTypeConfiguration) {
		this.addressesOfServiceFiles = addressesOfServiceFiles;
		this.addressOfClassConfiguration = addressOfClassConfiguration;
		this.addressOfTypeConfiguration = addressOfTypeConfiguration;
	}

	public List<String> getAddressesOfServiceFiles() {
		return addressesOfServiceFiles;
	}

	public String getAddressOfClassConfiguration() {
		return addressOfClassConfiguration;
	}

	public String getAddrOfTypeConfig() {
		return addressOfTypeConfiguration;
	}
}
