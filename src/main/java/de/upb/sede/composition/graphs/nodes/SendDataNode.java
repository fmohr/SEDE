package de.upb.sede.composition.graphs.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import de.upb.sede.composition.gc.ResolveInfo;

public class SendDataNode extends BaseNode{
	
	private final String fieldname;

	private final String targetAddress;
	
	public SendDataNode(String fieldname, String targetAddress) {
		super();
		this.fieldname = fieldname;
		this.targetAddress = targetAddress;
	}
	
	
	@Override
	public boolean producesField(String fieldname, ResolveInfo resolveInfo) {
		return false;
	}

	@Override
	public Collection<String> consumingFields(ResolveInfo resolveInfo) {
		return new ArrayList<String>() {{add(fieldname);}};
	}

	@Override
	public Collection<String> producingFields(ResolveInfo resolveInfo) {
		return Collections.EMPTY_LIST;
	}
}
