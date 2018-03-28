package de.upb.sede.services;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class Service implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6016176195313769529L;
	List<File> linkedFiles;
	File serviceFile;
	ServiceMetaInformation metaInfos = new ServiceMetaInformation();

	public Service(File serviceFile) {
		this.serviceFile = serviceFile;
	}

	public Service(File serviceFile, List<File> linkedFiles) {
		this.serviceFile = serviceFile;
		this.linkedFiles = linkedFiles;
	}

	public List<File> getLinkedFiles() {
		return linkedFiles;
	}

	public boolean hasLinkedFiles() {
		return (linkedFiles == null || linkedFiles.size() == 0);
	}

	public void setMetaInfos(ServiceMetaInformation metaInfos) {
		this.metaInfos = metaInfos;
	}

	public File getServiceFile() {
		return serviceFile;
	}

	public ServiceMetaInformation getMetaInfosCopy() {
		return metaInfos.deepCopy();
	}

	public ServiceMetaInformation getMetaInfos() {
		return metaInfos;
	}

	/**
	 * Create a new instance of this service.
	 * 
	 * @param name
	 *            Name that the instance shall have.
	 * @param params
	 *            Parameters to call the constructor with.
	 * @return Key of the new instance (appended namespace).
	 */
	public abstract String newInstance(String name, Map<String, Object> params);

	/**
	 * Instance with the given key is being serialized to the drive.
	 * 
	 * @param key
	 *            Key of the instance that shall be serialized.
	 */
	public abstract void serializeInstance(String key);

	/**
	 * Load a instance from the drive with the given key.
	 * 
	 * @param key
	 *            Key of the instance to load.
	 */
	public abstract void startSerializedInstance(String key);

	/**
	 * Kills a instance.
	 * 
	 * @param key
	 *            Key of the instance to kill.
	 */
	public abstract void killInstance(String key);

	/**
	 * Returns the instance with the given key.
	 * 
	 * @param key
	 *            Key of the instance to return.
	 * @return The demanded instance.
	 */
	public abstract ServiceInstance getInstance(String key);
}
