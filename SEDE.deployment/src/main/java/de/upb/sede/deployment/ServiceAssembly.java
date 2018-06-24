package de.upb.sede.deployment;

import java.util.Collection;
import java.util.Map;

import org.json.simple.JSONObject;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.util.JsonSerializable;

public class ServiceAssembly  implements JsonSerializable{
	private Collection<byte[]> serviceFiles;
	private ClassesConfig classConfiguration;
	private OnthologicalTypeConfig typeConfiguration;

	public ServiceAssembly(Collection<byte[]> serviceFiles, ClassesConfig classConfig,
			OnthologicalTypeConfig typeConfig) {
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

	@Override
	public JSONObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fromJson(Map<String, Object> data) {
		// TODO Auto-generated method stub
		
	}

}
