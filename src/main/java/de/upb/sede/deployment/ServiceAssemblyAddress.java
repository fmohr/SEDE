package de.upb.sede.deployment;

import java.util.Collection;

public class ServiceAssemblyAddress {
	private final Collection<String> addressesOfServiceFiles;
	private final String addressOfClassConfiguration;
	private final String addressOfTypeConfiguration;

	public ServiceAssemblyAddress(Collection<String> addressesOfServiceFiles, String addressOfClassConfiguration,
			String addressOfTypeConfiguration) {
		this.addressesOfServiceFiles = addressesOfServiceFiles;
		this.addressOfClassConfiguration = addressOfClassConfiguration;
		this.addressOfTypeConfiguration = addressOfTypeConfiguration;
	}

	public Collection<String> getAddressesOfServiceFiles() {
		return addressesOfServiceFiles;
	}

	public String getAddressOfClassConfiguration() {
		return addressOfClassConfiguration;
	}

	public String getAddrOfTypeConfig() {
		return addressOfTypeConfiguration;
	}
}
