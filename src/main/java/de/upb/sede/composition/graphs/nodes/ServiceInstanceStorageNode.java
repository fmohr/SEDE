package de.upb.sede.composition.graphs.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.upb.sede.composition.gc.ResolveInfo;

public class ServiceInstanceStorageNode extends BaseNode{
	
	private final boolean isLoadInstruction;
	private final String serviceInstanceFieldname;
	private final String serviceClasspath;
	private final String id;
	
	/* a collection that is used by both producer and consumer method based on the isLoadInstruction flag. */
	private Collection<String> consumerProducerField;
	

	public ServiceInstanceStorageNode(boolean isLoadInstruction, String fieldname, String serviceclasspath, String id) {
		super();
		this.isLoadInstruction = isLoadInstruction;
		this.serviceInstanceFieldname = fieldname;
		this.serviceClasspath = serviceclasspath;
		this.id = id;
		
		consumerProducerField = new ArrayList<>();
		consumerProducerField.add(serviceInstanceFieldname);
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
