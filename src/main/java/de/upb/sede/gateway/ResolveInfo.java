package de.upb.sede.gateway;

import de.upb.sede.config.ClassesConfig;
import de.upb.sede.config.OnthologicalTypeConfig;
import de.upb.sede.requests.resolve.InputFields;
import de.upb.sede.requests.resolve.ResolvePolicy;

/**
 * This class bundles all classes whose pointers are needed for resolving a
 * composition graph.
 */
public class ResolveInfo {

	private ClassesConfig classesConfiguration;
	private InputFields inputFields;
	private ResolvePolicy resolvePolicy;
	private ExecutorCoordinator executorCoordinator;
	private ExecutorHandle clientExecutor;
	private OnthologicalTypeConfig typeConfig;

	public ClassesConfig getClassesConfiguration() {
		return classesConfiguration;
	}

	public void setClassesConfiguration(ClassesConfig classConfiguration) {
		this.classesConfiguration = classConfiguration;
	}

	public InputFields getInputFields() {
		return inputFields;
	}

	public void setInputFields(InputFields inputFields) {
		this.inputFields = inputFields;
	}

	public ResolvePolicy getResolvePolicy() {
		return resolvePolicy;
	}

	public void setResolvePolicy(ResolvePolicy resolvePolicy) {
		this.resolvePolicy = resolvePolicy;
	}

	public ExecutorCoordinator getExecutorCoordinator() {
		return executorCoordinator;
	}

	public void setExecutorCoordinator(ExecutorCoordinator executorCoordinator) {
		this.executorCoordinator = executorCoordinator;
	}

	public ExecutorHandle getClientExecutor() {
		return clientExecutor;
	}

	public void setClientExecutor(ExecutorHandle clientExecutor) {
		this.clientExecutor = clientExecutor;
	}

	public OnthologicalTypeConfig getTypeConfig() {
		return typeConfig;
	}

	public void setTypeConfig(OnthologicalTypeConfig typeConfig) {
		this.typeConfig = typeConfig;
	}

}
