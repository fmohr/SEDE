package de.upb.sede.deployment;

import java.util.Collection;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;

public class ServiceAssembly {
	private Collection<byte[]> serviceFiles;
	private ClassesConfig classConfiguration;
	private OnthologicalTypeConfig typeConfiguration;

	public ServiceAssembly(Collection<byte[]> serviceFiles, ClassesConfig classConfig, OnthologicalTypeConfig typeConfig) {
		this.serviceFiles = serviceFiles;
		this.classConfiguration = classConfig;
		this.typeConfiguration = typeConfig;
	}

	public Collection<byte[]> getServiceFiles() {
		return serviceFiles;
	}

	public ClassesConfig getClassConfiguration() {
		return classConfiguration;
	}

	public OnthologicalTypeConfig getTypeConfiguration() {
		return typeConfiguration;
	}

}
