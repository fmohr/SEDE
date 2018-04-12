package de.upb.sede.composition.graphs.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import de.upb.sede.composition.gc.ResolveInfo;

public class ServiceInstanceStorageNode extends BaseNode{
	
	private static final String NO_ID = "UNDEFINED ID";
	
	private final boolean isLoadInstruction;
	private final String serviceInstanceFieldname;
	private final String serviceClasspath;
	private final String id;
	private final boolean hasId;
	
	/* a collection that is used by both producer and consumer method based on the isLoadInstruction flag. */
	private Collection<String> consumerProducerField;
	
	
	private ServiceInstanceStorageNode(boolean isLoadInstruction, boolean hasId, String fieldname, String serviceclasspath, String id) {
		super();
		this.isLoadInstruction = isLoadInstruction;
		this.serviceInstanceFieldname = Objects.requireNonNull(fieldname);
		this.serviceClasspath = Objects.requireNonNull(serviceclasspath);
		this.id = Objects.requireNonNull(id);
		this.hasId = hasId;
		consumerProducerField = new ArrayList<>();
		consumerProducerField.add(serviceInstanceFieldname);
	}

	public ServiceInstanceStorageNode(boolean isLoadInstruction, String fieldname, String serviceclasspath, String id) {
		this(isLoadInstruction, true, fieldname, serviceclasspath, id);
	}
	
	public ServiceInstanceStorageNode(String fieldname, String serviceclasspath) {
		this(false, false, fieldname, serviceclasspath, NO_ID);
	}

	@Override
	public boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		return isLoadInstruction && this.serviceInstanceFieldname.equals(fieldname);
	}

	@Override
	public Collection<String> consumingFields(ResolveInfo resolveInfo) {
		if(isLoadInstruction) {
			return Collections.EMPTY_LIST;
		}
		else {
			return consumerProducerField;
		}
	}

	@Override
	public Collection<String> producingFields(ResolveInfo resolveInfo) {
		if(!isLoadInstruction)  {
			return Collections.EMPTY_LIST;
		}
		else {
			return consumerProducerField;
		}
	}
}
