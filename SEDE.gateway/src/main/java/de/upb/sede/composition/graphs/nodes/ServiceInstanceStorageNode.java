package de.upb.sede.composition.graphs.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class ServiceInstanceStorageNode extends BaseNode {

	private final Optional<String> instanceId;
	private final String serviceInstanceFieldname;
	private final String serviceClasspath;

	public final boolean isLoadInstruction() {
		return instanceId.isPresent();
	}

	public final String getId() {
		return instanceId.get();
	}

	public final String getServiceInstanceFieldname() {
		return serviceInstanceFieldname;
	}

	public final String getServiceClasspath() {
		return serviceClasspath;
	}

	private ServiceInstanceStorageNode(Optional<String> instanceId, String fieldname, String serviceclasspath) {
		super();
		this.instanceId = instanceId;
		this.serviceInstanceFieldname = Objects.requireNonNull(fieldname);
		this.serviceClasspath = Objects.requireNonNull(serviceclasspath);
	}

	public ServiceInstanceStorageNode(String instanceId, String fieldname, String serviceclasspath) {
		this(Optional.of(instanceId), fieldname, serviceclasspath);
	}

	public ServiceInstanceStorageNode(String fieldname, String serviceclasspath) {
		this(Optional.empty(), fieldname, serviceclasspath);
	}

	public String toString() {
		return (isLoadInstruction() ? "load" : "store") + " \"" + getServiceInstanceFieldname() + "\"";
	}
}
