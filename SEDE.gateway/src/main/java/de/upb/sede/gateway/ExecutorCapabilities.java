package de.upb.sede.gateway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExecutorCapabilities {
	public static final String javalibs = "JAVA-LIB";
	public static final String pythonlibs = "PYTHON-LIB";

	private final List<String> executorCapabilities;
	private final List<String> supportedServiceClasses;

	public ExecutorCapabilities(String... capabilities) {
		List<String> capabilityList = new ArrayList<>();
		this.supportedServiceClasses = new ArrayList<>();
		for (String capability : capabilities) {
			capabilityList.add(capability);
		}
		executorCapabilities = Collections.unmodifiableList(capabilityList);
	}

	public void addAllServiceClasses(String... newServiceClasses) {
		Objects.requireNonNull(newServiceClasses);
		for (String serviceClass : newServiceClasses) {
			supportedServiceClasses.add(serviceClass);
		}
	}

	public List<String> supportedServices(){
		return Collections.unmodifiableList(supportedServiceClasses);
	}

	public boolean supportsServiceClass(String serviceClasspath) {
		return supportedServiceClasses.contains(serviceClasspath);
	}

	public boolean isCapableOf(String capability) {
		return executorCapabilities.contains(capability);
	}

	public boolean canCastInPlace() {
		return isCapableOf("cast_in_place");
	}
}
