package de.upb.sede.composition.graphs.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ServiceInstanceStorageNode extends BaseNode {

	private final boolean isLoadInstruction;
	private final String serviceInstanceFieldname;
	private final String serviceClasspath;

	/*
	 * a collection that is used by both producer and consumer method based on the
	 * isLoadInstruction flag.
	 */
	private Collection<String> consumerProducerField;

	public final boolean isLoadInstruction() {
		return isLoadInstruction;
	}

	public final String getServiceInstanceFieldname() {
		return serviceInstanceFieldname;
	}

	public final String getServiceClasspath() {
		return serviceClasspath;
	}

	ServiceInstanceStorageNode(boolean isLoadInstruction, boolean hasId, String fieldname, String serviceclasspath) {
		super();
		this.isLoadInstruction = isLoadInstruction;
		this.serviceInstanceFieldname = Objects.requireNonNull(fieldname);
		this.serviceClasspath = Objects.requireNonNull(serviceclasspath);
		consumerProducerField = new ArrayList<>();
		consumerProducerField.add(serviceInstanceFieldname);
	}

	public ServiceInstanceStorageNode(boolean isLoadInstruction, String fieldname, String serviceclasspath) {
		this(isLoadInstruction, true, fieldname, serviceclasspath);
	}

	public String toString() {
		return (isLoadInstruction() ? "load" : "store") + " \"" + getServiceInstanceFieldname() + "\"";
	}
}
