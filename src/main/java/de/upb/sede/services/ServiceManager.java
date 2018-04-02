package de.upb.sede.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiceManager {
	private FileManager fileManager;
	private static String namespace = "test";
	private List<ServiceC> serviceCList = new ArrayList<ServiceC>();
	private List<ServiceJava> serviceJavaList = new ArrayList<ServiceJava>();
	private List<ServicePython> servicePythonList = new ArrayList<ServicePython>();

	/**
	 * Defines the directory where all deployed services are located and loads the
	 * plugin_bridge which is needed to communicate with services implemented in C.
	 * 
	 * @param serviceLocation
	 *            Directory of the services.
	 */
	public ServiceManager(File serviceLocation) {
		fileManager = new FileManager(serviceLocation);
	}

	public void storeService(byte[] data, String filename) {
		fileManager.storeService(data, filename);
	}

	/**
	 * Loads a deployed service.
	 * 
	 * @param serviceFilename
	 *            name of the service file.
	 */
	public void loadService(String serviceFilename, List<String> linkedFilenames) {
		try {
			File serviceFile = fileManager.loadService(serviceFilename);
			List<File> linkedFiles = new ArrayList<File>();
			Language serviceLanguage = fileManager.getLanguage(serviceFile);
			for (String linkedFilename : linkedFilenames) {
				File linkedFile = fileManager.loadService(linkedFilename);
				if (fileManager.getLanguage(linkedFile) != serviceLanguage) {
					System.err.println("The language of the service file must be the same as the linked files.");
					return;
				}
				linkedFiles.add(linkedFile);
			}
			switch (serviceLanguage) {
			case C:
				serviceCList.add(new ServiceC(serviceFile, linkedFiles));
				break;
			case JAVA:
				serviceJavaList.add(new ServiceJava(serviceFile));
				break;
			case PYTHON:
				servicePythonList.add(new ServicePython(serviceFile));
			default:
				System.err.println("Unknown language.");
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads a deployed service.
	 * 
	 * @param serviceFilename
	 *            name of the service file.
	 */
	public void loadService(String serviceFilename) {
		try {
			File serviceFile = fileManager.loadService(serviceFilename);
			switch (fileManager.getLanguage(serviceFile)) {
			case C:
				serviceCList.add(new ServiceC(serviceFile));
				break;
			case JAVA:
				serviceJavaList.add(new ServiceJava(serviceFile));
				break;
			case PYTHON:
				servicePythonList.add(new ServicePython(serviceFile));
			default:
				System.err.println("Unknown language.");
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes the service from the drive.
	 * 
	 * @param serviceMeta
	 *            Service to delete.
	 */
	public void deleteService(ServiceMetaInformation serviceMeta) {
		Service service = getServiceByMeta(serviceMeta);
		if (service == null) {
			System.err.println("Service: " + serviceMeta.getIDs().get(0) + " not found for deletion.");
			return;
		}
		service.getServiceFile().delete();
		if (service.hasLinkedFiles()) {
			for (File linkedFile : service.getLinkedFiles()) {
				linkedFile.delete();
			}
		}
	}

	private Service getServiceByMeta(ServiceMetaInformation serviceMeta) {
		for (Service service : getServices()) {
			if (service.getMetaInfos().equals(serviceMeta))
				return service;
		}
		return null;
	}

	public boolean unloadService(Service service) {
		switch (service.getMetaInfos().getLanguage()) {
		case C:
			return serviceCList.remove(service);
		case JAVA:
			return serviceJavaList.remove(service);
		case PYTHON:
			return servicePythonList.remove(service);
		default:
			return false;
		}
	}

	public List<Service> getServices() {
		List<Service> result = new ArrayList<>();
		result.addAll(serviceCList);
		result.addAll(serviceJavaList);
		result.addAll(servicePythonList);
		return result;
	}

	/**
	 * Returns a list of all the deployed Plugins.
	 * 
	 * @return A list of all the available/ deployed Plugins.
	 */
	public List<ServiceC> getPluginList() {
		return serviceCList;
	}

	/**
	 * Returns a list of all the deployed Java based Services.
	 * 
	 * @return A list of all the available/ deployed Java based Services.
	 */
	public List<ServiceJava> getServiceJavaList() {
		return serviceJavaList;
	}

	/**
	 * Returns a list of all the deployed Python based Services.
	 * 
	 * @return A list of all the available/ deployed Python based Services.
	 */
	public List<ServicePython> getServicePythonList() {
		return servicePythonList;
	}

	/**
	 * Returns the currently configured namespace in which service instances are
	 * being created.
	 * 
	 * @return
	 */
	public static String getNameSpace() {
		return namespace;
	}

	public List<ServiceMetaInformation> getServicesMeta() {
		List<ServiceMetaInformation> result = new ArrayList<>();
		for (Service s : getServices())
			result.add(s.getMetaInfos());
		return result;
	}
}
